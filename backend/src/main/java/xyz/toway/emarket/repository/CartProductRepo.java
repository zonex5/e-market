package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.model.CartProductModel;

@Repository
public interface CartProductRepo extends ReactiveCrudRepository<CartProductModel, Integer> {

    Flux<CartProductModel> getAllByCustomerUuidAndLang(String uuid, String lang);
}
