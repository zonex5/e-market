package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.model.OrderDataItemModel;
import xyz.toway.emarket.model.OrderDataModel;

import java.util.Collection;

public interface OrderDataItemRepository extends ReactiveCrudRepository<OrderDataItemModel, Integer> {

    Flux<OrderDataItemModel> getAllByOrderIdAndLang(Integer orderId, String lang);
}
