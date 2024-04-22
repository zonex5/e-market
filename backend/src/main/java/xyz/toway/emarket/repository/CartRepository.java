package xyz.toway.emarket.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CartEntity;

public interface CartRepository extends ReactiveCrudRepository<CartEntity, Integer> {
    Mono<Void> deleteAllByCustomerIdAndProductId(Integer customerId, Integer productId);

    @Modifying
    @Query("UPDATE market.cart c SET quantity = :quantity WHERE id_customer = :customerId AND id_product = :productId")
    Mono<Void> updateQuantity(Integer customerId, Integer productId, Integer quantity);
}
