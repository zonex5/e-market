package xyz.toway.emarket.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CustomerDataModel;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.service.CartService;
import xyz.toway.emarket.service.CustomerService;

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

    @QueryMapping("newOrderData")
    public Mono<Object> shippingMethod() {
        return Mono.just(new Object());
    }

    @QueryMapping("getCustomerCart")
    public Mono<CartModel> getCustomerCart(@ContextValue(name = "customer", required = false) String uuid, @ContextValue(required = false) String language) {
        return customerService.getCustomerCart(uuid, language)
                .defaultIfEmpty(new CartModel());
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
