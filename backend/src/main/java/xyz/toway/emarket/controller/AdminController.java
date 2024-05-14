package xyz.toway.emarket.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CategoryEntity;
import xyz.toway.emarket.entity.CategoryTranslationEntity;
import xyz.toway.emarket.entity.ProductTranslationEntity;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.service.AdminService;
import xyz.toway.emarket.service.CategoryService;
import xyz.toway.emarket.service.ProductService;

import java.util.Objects;

@CrossOrigin
@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @QueryMapping("admAllCategories")
    public Flux<CategoryEntity> admAllCategories() {
        return categoryService.getAllCategories();
    }

    @QueryMapping("admListCategories")
    public Flux<CategoryModel> admListCategories() {
        return categoryService.getCategoriesByLang("us")
                .startWith(new CategoryModel(-1, "[Without category]", true));
    }

    @QueryMapping("admAllProducts")
    public Flux<AdmProductModel> admAllProducts(@Argument Integer categoryId, @Argument SortInput sort) {
        sort = Objects.requireNonNullElse(sort, new SortInput("title", "ASC"));
        if (categoryId == null) {
            return adminService.getAllProducts(sort);
        } else if (categoryId == -1) {
            return adminService.getProductsWithoutCategory(sort);
        } else {
            return adminService.getAllProductsByCategory(categoryId, sort);
        }
    }

    @QueryMapping("admOrders")
    public Flux<OrderDataModel> admOrders(@Argument SortInput sort) {
        return adminService.getOrders(sort);
    }

    @MutationMapping("admSaveCategory")
    public Mono<Boolean> saveAdmCategory(@Argument InputModel category) {
        return categoryService.saveCategory(category);
    }

    @MutationMapping("admSaveProduct")
    public Mono<Boolean> saveProduct(@Argument InputModel product) {
        return productService.saveProduct(product);
    }

    @MutationMapping("admDeleteCategory")
    public Mono<Boolean> deleteCategory(@Argument Integer id) {
        return categoryService.deleteCategory(id);
    }

    @MutationMapping("admNewCategory")
    public Mono<Boolean> newCategory() {
        return categoryService.newCategory();
    }

    @MutationMapping("admNewProduct")
    public Mono<Boolean> newProduct() {
        return productService.newProduct();
    }

    @MutationMapping("admDeleteProduct")
    public Mono<Boolean> deleteProduct(@Argument Integer id) {
        return productService.deleteProduct(id);
    }

    @MutationMapping("admSetOrderStatus")
    public Mono<Boolean> setOrderStatus(@Argument Integer id, @Argument String status) {
        return adminService.setOrderStatus(id, status);
    }

    @SchemaMapping(typeName = "AdmCategory", field = "translations")
    public Flux<CategoryTranslationEntity> admCategoryTranslations(CategoryEntity category) {
        return categoryService.getCategoryTranslations(category.getId());
    }

    @SchemaMapping(typeName = "AdmProduct", field = "translations")
    public Flux<ProductTranslationEntity> admProductTranslations(AdmProductModel product) {
        return adminService.getProductTranslations(product.id());
    }

    @SchemaMapping(typeName = "AdmOrderData", field = "items")
    public Flux<OrderDataItemModel> admOrderDataItems(OrderDataModel order) {
        return adminService.getOrderItems(order.getId());
    }
}
