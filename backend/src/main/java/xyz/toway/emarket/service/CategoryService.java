package xyz.toway.emarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CategoryEntity;
import xyz.toway.emarket.model.CategoryModel;
import xyz.toway.emarket.model.CategoryTranslationModel;
import xyz.toway.emarket.repository.CategoryRepository;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepo;

    public Mono<CategoryModel> getCategoryById(Integer id, String lang) {
        return id != null ? categoryRepo.getById(id, lang) : Mono.empty();
    }

    public Flux<CategoryModel> getCategoriesByLang(String lang) {
        Objects.requireNonNull(lang);
        return categoryRepo.getAllByLang(lang);
    }

    public Flux<CategoryModel> getCategoriesByLang(boolean active, String lang) {
        return categoryRepo.getAllByActive(active, lang);
    }

    public Flux<CategoryEntity> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Flux<CategoryTranslationModel> getCategoryTranslations(Integer categoryId) {
        Objects.requireNonNull(categoryId);
        return categoryRepo.getCategoryTranslations(categoryId);
    }

    public Mono<CategoryModel> getCategoryByProduct(Integer id, String lang) {
       /* Objects.requireNonNull(id);
        return productRepository.findById(id)
                .flatMap(product -> product.getCategoryId() != null ? categoryRepo.getById(product.getCategoryId(), lang) : Mono.empty());*/

        return Mono.empty();
    }

    /*public Mono<CategoryModel> addCategory(CategoryInputModel categoryInput) {
        Objects.requireNonNull(categoryInput);
        var entity = converter.categoryToEntity(categoryInput);
        return categoryRepo.save(entity).map(converter::categoryToModel);
    }*/

    /*@Transactional
    public Mono<CategoryModel> updateCategory(CategoryInputModel categoryInput) {
        *//*Objects.requireNonNull(categoryInput);
        Objects.requireNonNull(categoryInput.getId());
        return categoryRepo.findById(categoryInput.getId()).flatMap(entity -> {
            entity.setActive(Objects.requireNonNullElse(categoryInput.getActive(), true));
            entity.setTitle(categoryInput.getTitle());
            entity.setAnnotation(categoryInput.getAnnotation());
            return categoryRepo.save(entity);
        }).map(converter::categoryToModel);*//*
        return Mono.empty();
    }*/

    /*public Mono<Boolean> deleteCategory(Integer categoryId) {
        return categoryRepo.deleteById(categoryId).then(Mono.just(true)).defaultIfEmpty(false).onErrorReturn(false);
    }*/
}
