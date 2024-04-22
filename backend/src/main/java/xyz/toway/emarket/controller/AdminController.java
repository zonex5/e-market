package xyz.toway.emarket.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.entity.CategoryEntity;
import xyz.toway.emarket.model.CategoryTranslationModel;
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

//    @MutationMapping("saveAdmCategory")
//    public Mono<Boolean> saveAdmCategory() {
//        return Mono.just(true);
//    }

    @SchemaMapping(typeName = "AdmCategory", field = "translations")
    public Flux<CategoryTranslationModel> admCategoryTranslations(CategoryEntity category) {
        return categoryService.getCategoryTranslations(category.getId());
    }
}
