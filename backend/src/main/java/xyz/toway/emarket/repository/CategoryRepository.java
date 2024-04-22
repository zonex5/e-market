package xyz.toway.emarket.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CategoryEntity;
import xyz.toway.emarket.model.CategoryModel;
import xyz.toway.emarket.model.CategoryTranslationModel;

public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, Integer> {
    @Query("select * from market.category_details where lang = :lang")  //todo replace with query methods
    Flux<CategoryModel> getAllByLang(String lang);

    @Query("select * from market.category_details where lang = :lang and active = :active") //todo replace with query methods
    Flux<CategoryModel> getAllByActive(boolean active, String lang);

    @Query("select * from market.category_details where lang = :lang and id = :id") //todo replace with query methods
    Mono<CategoryModel> getById(Integer id, String lang);

    @Query("select * from market.category_translation where id_category = :categoryId")
    Flux<CategoryTranslationModel> getCategoryTranslations(Integer categoryId);
}
