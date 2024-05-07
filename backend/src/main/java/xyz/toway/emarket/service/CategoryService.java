package xyz.toway.emarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CategoryEntity;
import xyz.toway.emarket.model.CategoryModel;
import xyz.toway.emarket.entity.CategoryTranslationEntity;
import xyz.toway.emarket.model.InputModel;
import xyz.toway.emarket.model.TranslationInputModel;
import xyz.toway.emarket.repository.CategoryRepository;
import xyz.toway.emarket.repository.CategoryTranslationRepo;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepo;
    private final CategoryTranslationRepo categoryTranslationRepo;

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

    public Flux<CategoryTranslationEntity> getCategoryTranslations(Integer categoryId) {
        Objects.requireNonNull(categoryId);
        return categoryRepo.getCategoryTranslations(categoryId);
    }

    public Mono<Boolean> saveCategory(InputModel input) {  //todo transactional
        Objects.requireNonNull(input);

        return Mono.justOrEmpty(input.getId())
                .flatMap(id -> categoryRepo.findById(id)
                        .flatMap(category -> {
                            category.setActive(input.getActive());
                            return categoryRepo.save(category);
                        }))
                .switchIfEmpty(categoryRepo.save(new CategoryEntity())) //todo
                .flatMap(category -> saveTranslations(category.getId(), input.getTranslations()));
    }

    private Mono<Boolean> saveTranslations(Integer id, List<TranslationInputModel> translations) {
        return categoryTranslationRepo.deleteAllByCategoryId(id)
                .thenMany(Flux.fromIterable(translations))
                .flatMap(translation -> {
                    CategoryTranslationEntity translationEntity = new CategoryTranslationEntity(id, translation.getTitle(), translation.getLang());
                    return categoryTranslationRepo.save(translationEntity);
                })
                .then(Mono.just(true))
                .onErrorResume(ex -> Mono.just(false));
    }

    public Mono<Boolean> deleteCategory(Integer id) {
        return categoryRepo.deleteById(id)
                .thenReturn(true)
                .onErrorResume(ex -> Mono.just(false));
    }

    public Mono<Boolean> newCategory() {
        TranslationInputModel defaultTranslation = new TranslationInputModel();
        defaultTranslation.setLang("us");
        defaultTranslation.setTitle("New Category");

        InputModel model = new InputModel();
        model.getTranslations().add(defaultTranslation);

        return saveCategory(model);
    }
}
