package xyz.toway.emarket.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ProductTranslationEntity;

@Repository
public interface ProductTranslationRepo extends ReactiveCrudRepository<ProductTranslationEntity, Integer> {

    Mono<Void> deleteAllByProductId(Integer id);

    //Flux<ProductTranslationEntity> getAllByProductId(Integer id);

    @Query("select id as id_product, title, annotation, description, lang from market.products_details where id = :id")
    Flux<ProductTranslationEntity> getAllByProductId(Integer id);
}
