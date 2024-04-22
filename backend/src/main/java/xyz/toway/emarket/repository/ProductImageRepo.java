package xyz.toway.emarket.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.entity.ImageToProductEntity;

public interface ProductImageRepo extends ReactiveCrudRepository<ImageToProductEntity, Integer> {
    Flux<ImageToProductEntity> getAllByProductId(Integer id);
}
