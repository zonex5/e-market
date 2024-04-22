package xyz.toway.emarket.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ImageEntity;

@Repository
public interface ImageRepository extends ReactiveCrudRepository<ImageEntity, Integer> {
    @Query("select id_image from market.image_to_product where is_thumbnail = true and id_product = :id limit 1")
    Mono<Integer> getProductThumbnail(Integer id);

    @Query("select id_image from market.image_to_product where id_product = :id order by is_thumbnail desc")
    Flux<Integer> getProductImages(Integer id);
}
