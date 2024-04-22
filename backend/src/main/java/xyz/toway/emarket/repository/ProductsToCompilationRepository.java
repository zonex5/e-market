package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ProductToCompilationEntity;

public interface ProductsToCompilationRepository extends ReactiveCrudRepository<ProductToCompilationEntity, Integer> {
    Mono<Void> deleteAllByCompilationId(Integer id);
}
