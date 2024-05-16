package xyz.toway.emarket.repository;


import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ImageToProductEntity;

public interface ProductImageRepo extends ReactiveCrudRepository<ImageToProductEntity, Integer> {

    Flux<ImageToProductEntity> getAllByProductIdOrderByImageId(Integer id);

    @Query("update market.image_to_product set is_thumbnail = false where id_product = :productId")
    Mono<Void> removeThumbnailsMyProduct(Integer productId);

    @Query("update market.image_to_product set is_thumbnail = true where id_image = :imageId and id_product = :productId")
    Mono<Void> setThumbnailByProductId(Integer imageId, Integer productId);
}
