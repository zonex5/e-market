package xyz.toway.emarket.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.WishListEntity;
import xyz.toway.emarket.model.ProductModel;

@Repository
public interface WishListRepo extends ReactiveCrudRepository<WishListEntity, Integer> {

    Mono<Void> deleteAllByCustomerIdAndProductId(Integer customerId, Integer productId);

    Mono<Boolean> existsByCustomerIdAndProductId(Integer customerId, Integer productId);

    @Query("select * from market.product_favourites where id_customer = :customerId and lang = :lang")
    Flux<ProductModel> getFavourites(Integer customerId, String lang);
}
