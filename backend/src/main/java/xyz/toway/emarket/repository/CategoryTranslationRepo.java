package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CategoryTranslationEntity;

@Repository
public interface CategoryTranslationRepo extends ReactiveCrudRepository<CategoryTranslationEntity, Integer> {

    Mono<Void> deleteAllByCategoryId(Integer id);
}
