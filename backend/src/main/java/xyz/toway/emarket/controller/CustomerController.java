package xyz.toway.emarket.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CustomerDataModel;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.service.CartService;
import xyz.toway.emarket.service.CustomerService;

import javax.annotation.security.RolesAllowed;

@CrossOrigin
@Controller
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CartService cartService;

    @MutationMapping("finishOrder")
    public Mono<OrderResult> finishOrder(@Argument OrderInputModel data, @ContextValue(name = "customer", required = false) String uuid) {
        return customerService.finishOrder(data, uuid);
    }

    @MutationMapping("saveCustomerData")
    public Mono<Boolean> saveCustomerData(@Argument CustomerDataInputModel data, @ContextValue(name = "customer", required = false) String uuid) {
        data.setUuid(uuid);
        return customerService.saveCustomerData(data);
    }

    @QueryMapping("newOrderData")
    public Mono<Object> shippingMethod() {
        return Mono.just(new Object());
    }

    @QueryMapping("getCustomerCart")
    public Mono<CartModel> getCustomerCart(@ContextValue(name = "customer", required = false) String uuid, @ContextValue(required = false) String language) {
        return customerService.getCustomerCart(uuid, language)
                .defaultIfEmpty(new CartModel());
    }

    @QueryMapping("getOrderData")
    @RolesAllowed("ROLE_CUSTOMER")
    public Flux<OrderDataModel> getOrderData(@Argument String status, @ContextValue(name = "customer", required = false) String uuid) {
        return customerService.getOrderData(uuid, status);
    }

    @SchemaMapping(typeName = "OrderData", field = "items")
    public Flux<OrderDataItemModel> getOrderDataItems(OrderDataModel order, @ContextValue(required = false) String language) {
        if (order != null && order.getId() != null) {
            return customerService.getOrderItems(order.getId(), language);
        }
        return Flux.empty();
    }

    @SchemaMapping(typeName = "NewOrderData", field = "customerData")
    public Mono<CustomerDataModel> getNewOrderCustomerData(@ContextValue(name = "customer", required = false) String uuid) {
        return customerService.getCustomerData(uuid);
    }

    @SchemaMapping(typeName = "NewOrderData", field = "amounts")
    public Mono<OrderAmountsModel> getNewOrderAmountsData(@ContextValue(name = "customer", required = false) String uuid, @ContextValue(required = false) String language) {
        return customerService.getNewOrderAmounts(uuid);
    }

    @SchemaMapping(typeName = "NewOrderData", field = "shippingMethod")
    public Mono<ShippingMethodModel> getShippingMethod(@ContextValue(name = "customer", required = false) String uuid, @ContextValue(required = false) String language) {
        return customerService.getShippingMethod(uuid);
    }
}
