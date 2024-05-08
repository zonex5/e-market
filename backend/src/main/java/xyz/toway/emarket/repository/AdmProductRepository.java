package xyz.toway.emarket.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.model.AdmProductModel;

@Repository
public interface AdmProductRepository extends ReactiveSortingRepository<AdmProductModel, Integer> {

    Flux<AdmProductModel> findByIdNotNull(Pageable pageable);

    Flux<AdmProductModel> findByCategoryId(Integer categoryId, Pageable pageable);

    Flux<AdmProductModel> findByCategoryIdIsNull(Pageable pageable);
}
