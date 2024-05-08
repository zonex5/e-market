package xyz.toway.emarket.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.service.CartService;
import xyz.toway.emarket.service.CategoryService;
import xyz.toway.emarket.service.ProductService;

import javax.annotation.security.RolesAllowed;

@CrossOrigin
@Controller
@AllArgsConstructor
public class MarketController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @QueryMapping("getActiveCategories")
    public Flux<CategoryModel> getActiveCategories(@ContextValue(required = false) String language) {
        return categoryService.getCategoriesByLang(true, language);
    }

    @QueryMapping("getProductById")
    public Mono<ProductModel> getProductById(@Argument Integer id, @ContextValue(required = false) String language) {
        return productService.getProductById(id, language);
    }

    @QueryMapping("productsByCategory")
    public Flux<ProductModel> productsByCategory(@Argument Integer id, @Argument SortInput sort, @ContextValue(required = false) String language) {
        return productService.productsByCategory(id, language, sort);
    }

    @QueryMapping("favourites")
    public Flux<ProductModel> favourites(@ContextValue(name = "customer", required = false) String uuid, @ContextValue(required = false) String language) {
        return productService.favourites(uuid, language);
    }

    @QueryMapping("totalProductsByCategory")
    public Mono<Integer> totalProductsByCategory(@Argument Integer id, @ContextValue(required = false) String language) {
        return productService.totalProductsByCategory(id, language);
    }

    @QueryMapping("productsByText")
    public Flux<ProductModel> productsByText(@Argument String text, @Argument SortInput sort, @ContextValue(required = false) String language) {
        return productService.productsByText(text, language, sort);
    }

    @QueryMapping("totalProductsByText")
    public Mono<Integer> totalProductsByText(@Argument String text, @ContextValue(required = false) String language) {
        return productService.totalProductsByText(text, language);
    }

    @QueryMapping("productsMainPage")
    public Flux<ProductModel> productsMainPage(@Argument SortInput sort, @ContextValue(required = false) String language) {
        return productService.productsMainPage(language, sort);
    }

    @QueryMapping("productsRandom")
    public Flux<ProductModel> productsRandomSearchPage(@Argument SortInput sort, @ContextValue(required = false) String language) {
        return productService.productsRandomSearchPage(language, sort);
    }

    @MutationMapping("addProductToCart")
    @RolesAllowed("ROLE_CUSTOMER")
    public Mono<Boolean> addProductToCart(@Argument Integer id, @Argument Integer quantity, @ContextValue(name = "customer", required = false) String uuid) {
        return cartService.addProduct(id, quantity, uuid);
    }

    @MutationMapping("updateQuantityInCart")
    @RolesAllowed("ROLE_CUSTOMER")
    public Mono<Boolean> changeQuantityInCart(@Argument Integer id, @Argument Integer quantity, @ContextValue(name = "customer", required = false) String uuid) {
        return cartService.updateQuantity(id, quantity, uuid);
    }

    @MutationMapping("productToWishList")
    @RolesAllowed("ROLE_CUSTOMER")
    public Mono<Boolean> addProductToWishList(@Argument Integer id, @Argument Boolean flag, @ContextValue(name = "customer", required = false) String uuid) {
        return productService.addToWishList(id, flag, uuid);
    }

    @MutationMapping("deleteProductFromCart")
    @RolesAllowed("ROLE_CUSTOMER")
    public Mono<Boolean> deleteProductFromCart(@Argument Integer id, @ContextValue(name = "customer", required = false) String uuid) {
        return cartService.removeProduct(id, uuid);
    }

    @SchemaMapping(typeName = "Category", field = "products")
    public Flux<ProductModel> getCategoryProducts(@NonNull CategoryModel category, @ContextValue(required = false) String language) {
        return productService.getProductsByCategory(category.id(), language);
    }

    @SchemaMapping(typeName = "Product", field = "category")
    public Mono<CategoryModel> getProductCategory(@NonNull ProductModel product, @ContextValue(required = false) String language) {
        return categoryService.getCategoryById(product.categoryId(), language);
    }

    @SchemaMapping(typeName = "Product", field = "price")
    public Mono<PriceModel> getPriceProduct(@NonNull ProductModel product) {
        return productService.extractPriceModel(product);
    }

    @SchemaMapping(typeName = "Product", field = "images")
    public Flux<Integer> getImagesProduct(@NonNull ProductModel product) {
        return productService.getImagesByProduct(product.id());
    }

    @SchemaMapping(typeName = "Product", field = "inWishList")
    public Mono<Boolean> getInWishListProduct(@NonNull ProductModel product, @ContextValue(name = "customer", required = false) String uuid) {
        if (uuid != null && uuid.length() > 1) {
            return productService.inWishList(product.id(), uuid);
        }
        return Mono.just(false);
    }

    @SchemaMapping(typeName = "Product", field = "attributes")
    public Flux<ProductGeneralVariantModel> getProductAttributes(@NonNull ProductModel product) {
        return productService.getProductAttributes(product.id());
    }

    @SchemaMapping(typeName = "Product", field = "variants")
    public Flux<ProductVariantModel> getProductVariants(@NonNull ProductModel product) {
        return productService.getProductVariants(product.id());
    }

    @SchemaMapping(typeName = "ProductVariant", field = "attributes")
    public Flux<AttributeModel> getProductVariantAttributes(@NonNull ProductVariantModel variant) {
        return productService.getProductVariantAttributes(variant.productId());
    }
}