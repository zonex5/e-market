package xyz.toway.emarket.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CategoryEntity;
import xyz.toway.emarket.entity.CategoryTranslationEntity;
import xyz.toway.emarket.model.InputModel;
import xyz.toway.emarket.service.AdminService;
import xyz.toway.emarket.service.CategoryService;

@CrossOrigin
@Controller
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CategoryService categoryService;

    @QueryMapping("admAllCategories")
    public Flux<CategoryEntity> admAllCategories() {
        return categoryService.getAllCategories();
    }

    @MutationMapping("admSaveCategory")
    public Mono<Boolean> saveAdmCategory(@Argument InputModel category) {
        return categoryService.saveCategory(category);
    }

    @MutationMapping("admDeleteCategory")
    public Mono<Boolean> deleteCategory(@Argument Integer id) {
        return categoryService.deleteCategory(id);
    }

    @MutationMapping("admNewCategory")
    public Mono<Boolean> newCategory() {
        return categoryService.newCategory();
    }

    @SchemaMapping(typeName = "AdmCategory", field = "translations")
    public Flux<CategoryTranslationEntity> admCategoryTranslations(CategoryEntity category) {
        return categoryService.getCategoryTranslations(category.getId());
    }
}
