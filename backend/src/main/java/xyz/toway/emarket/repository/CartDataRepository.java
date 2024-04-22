package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.model.CartDataModel;

@Repository
public interface CartDataRepository extends ReactiveCrudRepository<CartDataModel, Integer> {
    Flux<CartDataModel> getAllByCustomerId(Integer customerId);
}
