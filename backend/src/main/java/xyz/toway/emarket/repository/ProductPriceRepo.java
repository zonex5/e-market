package xyz.toway.emarket.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ProductPriceEntity;
import xyz.toway.emarket.model.PriceModel;

public interface ProductPriceRepo extends ReactiveCrudRepository<ProductPriceEntity, Integer> {

    @Query("select old_price, new_price, discount, from_date, sale from market.product_price where id_product = :id order by from_date desc limit 1;")
    Mono<PriceModel> getByProduct(Integer id);

}
