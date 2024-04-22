package xyz.toway.emarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CartEntity;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.repository.CartProductRepo;
import xyz.toway.emarket.repository.CartRepository;
import xyz.toway.emarket.repository.CustomerRepository;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CartProductRepo cartProductRepo;

    public Mono<Boolean> addProduct(Integer id, Integer quantity, String customerUuid) {
        return customerRepository.getFirstByUuid(customerUuid)
                .flatMap(c -> cartRepository.save(new CartEntity(id, quantity, c.getId())))
                .then(Mono.just(true))
                .defaultIfEmpty(false)
                .onErrorReturn(false);
    }

    public Mono<Boolean> removeProduct(Integer id, String customerUuid) {
        return customerRepository.getFirstByUuid(customerUuid)
                .flatMap(c -> cartRepository.deleteAllByCustomerIdAndProductId(c.getId(), id))
                .then(Mono.just(true))
                .defaultIfEmpty(false)
                .onErrorReturn(false);
    }

    public Flux<CartProductModel> getCartProducts(String userUuid, String lang) {
        Objects.requireNonNull(userUuid);
        Objects.requireNonNull(lang);
        return cartProductRepo.getAllByCustomerUuidAndLang(userUuid, lang);
    }

    public Mono<Boolean> updateQuantity(Integer id, Integer quantity, String customerUuid) {
        return customerRepository.getFirstByUuid(customerUuid)
                .flatMap(c -> cartRepository.updateQuantity(c.getId(), id, quantity))
                .then(Mono.just(true))
                .defaultIfEmpty(false)
                .onErrorReturn(false);
    }
}
