package xyz.toway.emarket.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ProductEntity;
import xyz.toway.emarket.model.AttributeModel;
import xyz.toway.emarket.model.ProductGeneralVariantModel;
import xyz.toway.emarket.model.ProductModel;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
    /*@Query("select * from market.products_details where lang = :lang")
    Flux<ProductModel> getAll(String lang, Sort sort);*/

    /*@Query("select * from market.products_details where lang = :lang and active = :active")
    Flux<ProductModel> getAllByActive(Boolean active, String lang, Sort sort);*/

    @Query("select * from market.products_details where lang = :lang and id_category = :categoryId")
    Flux<ProductModel> getProductsByCategory(Integer categoryId, String lang, Pageable pageable);

    @Query("select * from market.products_details")
    Flux<ProductModel> getAllMy(Pageable pageable);

    @Query("select * from market.products_details where lang = :lang and id_category = :categoryId and available = :inStock")
    Flux<ProductModel> getAllByCategoryAndStock(Integer categoryId, Boolean inStock, String lang, Sort sort);

    /*@Query("select * from market.products_details where lang = :lang and id_category is null")
    Flux<ProductModel> getAllOrphans(String lang, Sort sort);*/

    @Query("select * from market.products_details where lang = :lang and id = :id")
    Mono<ProductModel> getById(Integer id, String lang);

    @Query("select * from market.cart_products where lang = :lang and uuid_customer = :uuid")
    Flux<ProductModel> getCartProducts(String uuid, String lang, Sort sort);

    @Query("select * from market.product_general_variants where id_parent = :productId")
    Flux<ProductGeneralVariantModel> getProductAttributes(Integer productId);

    @Query("select * from product.product_general_data where id_parent = :productId")
    Flux<ProductModel> getProductVariants(Integer productId);

    @Query("select * from product.product_attributes_data where id_product = :productId")
    Flux<AttributeModel> getProductVariantAttributes(Integer productId);
}
