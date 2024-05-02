package xyz.toway.emarket.service;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.*;
import xyz.toway.emarket.helper.enums.OrderStatus;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.repository.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDataRepository customerDataRepository;  //todo customer_data -> customer_saved_date
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderDataRepository orderDataRepository;
    private final CartDataRepository cartDataRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartProductRepo cartProductRepo;
    private final OrderDataModelRepository orderDataModelRepository;
    private final OrderDataItemRepository orderDataItemRepository;
    private final CartRepository cartRepository;

    private final ReactiveTransactionManager transactionManager;

    private static final List<String> ACTIVE_ORDERS_STATUS = List.of(
            OrderStatus.NEW.getValue(),
            OrderStatus.SHIPPED.getValue()
    );

    public Mono<CustomerDataModel> getCustomerData(String uuid) {
        Objects.requireNonNull(uuid);
        return customerRepository.getFirstByUuid(uuid)
                .flatMap(customer ->
                        customerDataRepository.getByCustomerId(customer.getId())
                                .map(data -> Boolean.TRUE.equals(data.getSave()) ? from(data) : from(customer))
                                .switchIfEmpty(Mono.just(from(customer)))
                );
    }

    public Mono<OrderResult> finishOrder(OrderInputModel data, String uuid) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(data);

        // find customer by uuid
        return customerRepository.getFirstByUuid(uuid)
                .flatMap(customer -> {
                    CustomerDataEntity customerData = from(data);
                    customerData.setCustomerId(customer.getId());
                    OrderEntity order = OrderEntity.createNewOrder();
                    order.setCustomerId(customer.getId());
                    OrderDataEntity orderData = orderDataFrom(data);

                    Mono<OrderResult> dataTransaction = TransactionalOperator
                            .create(transactionManager)
                            .transactional(
                                    // try to update existing customer data
                                    customerDataRepository.getByCustomerId(customerData.getCustomerId())
                                            .flatMap(cd -> {
                                                customerData.setId(cd.getId());
                                                return customerDataRepository.save(customerData);
                                            })
                                            // insert new customer data (address, name, etc.)
                                            .switchIfEmpty(customerDataRepository.save(customerData))
                                            // save order
                                            .then(orderRepository.save(order))
                                            // save order data (address, name, etc.)
                                            .flatMap(o -> {
                                                orderData.setOrderId(o.getId());
                                                return orderDataRepository.save(orderData);
                                            })
                                            // save order items
                                            .flatMap(od -> saveOrderItems(customer.getId(), od.getOrderId()))
                                            // empty cart
                                            .then(Mono.defer(() -> cartRepository.deleteAllByCustomerId(customer.getId())))
                                            .thenReturn(OrderResult.ok())
                            );

                    return checkOrderProducts(customer.getId())
                            .switchIfEmpty(dataTransaction);

                })
                .defaultIfEmpty(OrderResult.fail())
                .onErrorReturn(OrderResult.fail());
    }

    public Mono<OrderAmountsModel> getNewOrderAmounts(String uuid) {
        Objects.requireNonNull(uuid);

        var productsFlux = customerRepository.getFirstByUuid(uuid)
                .flatMapMany(c -> cartDataRepository.getAllByCustomerId(c.getId()));


        Mono<BigDecimal> totalCurrentPriceMono = productsFlux
                .map(p -> p.price().multiply(BigDecimal.valueOf(p.quantity())))
                .reduce(BigDecimal::add);

        return Mono.zip(totalCurrentPriceMono, getShippingMethod(uuid))
                .map(tuple -> new OrderAmountsModel(tuple.getT1(), tuple.getT2().getCost(), tuple.getT1().add(tuple.getT2().getCost())));
    }

    public Mono<CartModel> getCustomerCart(String userUuid, String lang) {
        Objects.requireNonNull(userUuid);
        Objects.requireNonNull(lang);

        var productsFlux = cartProductRepo.getAllByCustomerUuidAndLang(userUuid, lang);

        Mono<BigDecimal> totalCurrentPriceMono = productsFlux
                .map(p -> p.currentPrice().multiply(BigDecimal.valueOf(p.quantity())))
                .reduce(BigDecimal::add);

        Mono<BigDecimal> totalSavedAmountMono = productsFlux
                .map(p -> p.oldPrice().multiply(BigDecimal.valueOf(p.quantity()))
                        .subtract(p.currentPrice().multiply(BigDecimal.valueOf(p.quantity()))))
                .reduce(BigDecimal::add);

        return Mono.zip(productsFlux.collectList(), totalCurrentPriceMono, totalSavedAmountMono)
                .map(tuple -> new CartModel(tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    public Mono<ShippingMethodModel> getShippingMethod(String uuid) {  //todo
        ShippingMethodModel result = new ShippingMethodModel();
        result.setCost(BigDecimal.valueOf(12.50));
        result.setMethodName("Posta Moldovei");
        return Mono.just(result);
    }

    private Mono<Void> saveOrderItems(Integer customerId, Integer orderId) {
        return cartDataRepository.getAllByCustomerId(customerId)
                .map(item -> new OrderItemEntity(null, orderId, item.productId(), item.price(), item.quantity()))
                .collectList()
                .flatMap(items -> orderItemRepository.saveAll(items).then());
    }

    public Flux<OrderDataModel> getOrderData(String uuid, String status) {
        // all orders
        if (Strings.isNullOrEmpty(status) || status.equals(OrderStatus.ALL.getValue())) {
            return orderDataModelRepository.getAllByCustomerUuid(uuid);
        }

        // active orders
        if (status.equals(OrderStatus.ACTIVE.getValue())) {
            return orderDataModelRepository.getAllByCustomerUuidAndStatusIn(uuid, ACTIVE_ORDERS_STATUS);
        }

        // orders with other status
        return orderDataModelRepository.getAllByCustomerUuidAndStatusIn(uuid, List.of(status));
    }

    public Flux<OrderDataItemModel> getOrderItems(Integer orderId, String lang) {
        return orderDataItemRepository.getAllByOrderIdAndLang(orderId, lang);
    }

    private Mono<OrderResult> checkOrderProducts(Integer customerId) {
        return cartDataRepository.getAllByCustomerId(customerId)
                .filter(cd -> !cd.available())
                .map(CartDataModel::productId)
                .collectList()
                .flatMap(unavailableProducts -> unavailableProducts.isEmpty()
                        ? Mono.empty()
                        : Mono.just(OrderResult.fail().addUnavailableProducts(unavailableProducts)));
    }

    private CustomerDataEntity from(OrderInputModel order) {
        CustomerDataEntity entity = new CustomerDataEntity();
        entity.setFirstName(order.getFirstName());
        entity.setLastName(order.getLastName());
        entity.setEmail(order.getEmail());
        entity.setPhone(order.getPhone());
        entity.setCountryId(order.getCountry());
        entity.setAddress(order.getAddress());
        entity.setZip(order.getZip());
        entity.setCity(order.getCity());
        entity.setSave(order.getSave());
        return entity;
    }

    private OrderDataEntity orderDataFrom(OrderInputModel order) {
        OrderDataEntity entity = new OrderDataEntity();
        entity.setFirstName(order.getFirstName());
        entity.setLastName(order.getLastName());
        entity.setPhone(order.getPhone());
        entity.setCountryId(order.getCountry());
        entity.setAddress(order.getAddress());
        entity.setZip(order.getZip());
        entity.setCity(order.getCity());
        return entity;
    }

    private CustomerDataModel from(CustomerEntity entity) {
        return CustomerDataModel.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .save(false)
                .build();
    }

    private CustomerDataModel from(CustomerDataEntity entity) {
        return CustomerDataModel.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .city(entity.getCity())
                .countryId(entity.getCountryId())
                .zip(entity.getZip())
                .save(entity.getSave())
                .build();
    }

    public Mono<Boolean> saveCustomerData(CustomerDataInputModel data) {
        Objects.requireNonNull(data);

        return customerRepository.getFirstByUuid(data.getUuid())
                .flatMap(customer -> {
                    customer.setEmail(data.getEmail());
                    customer.setFirstName(data.getFirstName());
                    customer.setLastName(data.getLastName());
                    return customerRepository.save(customer);
                })
                .thenReturn(true);
    }
}
