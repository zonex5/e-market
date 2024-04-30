package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.model.OrderDataModel;

import java.util.Collection;

public interface OrderDataModelRepository extends ReactiveCrudRepository<OrderDataModel, Integer> {

    Flux<OrderDataModel> getAllByCustomerUuidAndStatusIn(String uuid, Collection<String> status);

    Flux<OrderDataModel> getAllByCustomerUuid(String uuid);
}
