package xyz.toway.emarket.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.model.ProductModel;

public interface ProductDetailsRepository extends ReactiveCrudRepository<ProductModel, Integer> {

    Flux<ProductModel> getAllByLangAndAvailable(String lang, Boolean available, Pageable pageable);

    Mono<Integer> countAllByLangAndAvailable(String lang, Boolean available);

    Flux<ProductModel> getAllByLangAndCategoryId(String lang, Integer categoryId, Pageable pageable);

    Mono<Integer> countAllByLangAndCategoryId(String lang, Integer categoryId);

    Flux<ProductModel> getAllByLangAndTitleContainingIgnoreCaseOrAnnotationContainingIgnoreCase(String lang, String v1, String v2, Pageable pageable);

    Mono<Integer> countAllByLangAndTitleContainingIgnoreCaseOrAnnotationContainingIgnoreCase(String lang, String v1, String v2);

    @Query("SELECT * FROM market.products_details WHERE available = true AND id_parent IS NULL AND lang = :lang ORDER BY random() LIMIT :count")
    Flux<ProductModel> randomProducts(String lang, int count);
}
