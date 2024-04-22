package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CustomerEntity;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Integer> {

    Mono<CustomerEntity> getFirstByUuid(String uuid);
}
