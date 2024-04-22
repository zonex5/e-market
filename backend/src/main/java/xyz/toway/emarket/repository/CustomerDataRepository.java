package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CustomerDataEntity;

public interface CustomerDataRepository extends ReactiveCrudRepository<CustomerDataEntity, Integer> {

    Mono<CustomerDataEntity> getByCustomerId(Integer customerId);
}
