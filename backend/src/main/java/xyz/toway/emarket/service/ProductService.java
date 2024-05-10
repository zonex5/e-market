package xyz.toway.emarket.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.*;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.repository.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final ProductPriceRepo productPriceRepo;
    private final ImageRepository imageRepository;
    private final ProductDetailsRepository productDetailsRepo;
    private final WishListRepo wishListRepo;
    private final CustomerRepository customerRepository;
    private final ProductTranslationRepo productTranslationRepo;

/*    public Flux<ProductModel> getAllProducts(String lang) {
        return productDetailsRepo.getAllByLang(lang, null);
    }*/

    /*public Flux<ProductModel> getActiveProducts(String lang) {
        return productRepo.getAllByActive(true, lang, null);
    }*/

    public Mono<ProductModel> getProductById(Integer id, String lang) {
        Objects.requireNonNull(id);
        return productRepo.getById(id, lang);
    }

    /*public Flux<ProductModel> getAllOrphans(String lang) {
        return productRepo.getAllOrphans(lang, null);
    }*/

    public Flux<ProductModel> getProductsByCategory(Integer id, String lang) {
        Objects.requireNonNull(id);
        return productRepo.getProductsByCategory(id, lang, null);
    }

    public Mono<Integer> getProductThumbnail(Integer productId) {
        Objects.requireNonNull(productId);
        return imageRepository.getProductThumbnail(productId);
    }

    public Mono<PriceModel> getPriceByProduct(Integer productId) {
        Objects.requireNonNull(productId);
        return productPriceRepo.getByProduct(productId);
    }

    public Mono<PriceModel> extractPriceModel(ProductModel product) {
        return product == null || product.currentPrice() == null
                ? Mono.empty()
                : Mono.just(new PriceModel(product.oldPrice(), product.newPrice(), product.currentPrice(), product.discount(), product.priceFromDate(), product.sale()));
    }

    public Flux<Integer> getImagesByProduct(Integer productId) {
        Objects.requireNonNull(productId);
        return imageRepository.getProductImages(productId);
    }

    public Flux<ProductModel> productsByCategory(Integer id, String lang, SortInput sort) {
        var page = convertSort(sort);
        return productDetailsRepo.getAllByLangAndCategoryId(lang, id, page);
    }

    public Mono<Integer> totalProductsByCategory(Integer id, String lang) {
        return productDetailsRepo.countAllByLangAndCategoryId(lang, id);
    }

    public Flux<ProductModel> productsByText(String text, String lang, SortInput sort) {
        var page = convertSort(sort);
        return productDetailsRepo.getAllByLangAndTitleContainingIgnoreCaseOrAnnotationContainingIgnoreCase(lang, text, text, page);
    }

    public Mono<Integer> totalProductsByText(String text, String lang) {
        return productDetailsRepo.countAllByLangAndTitleContainingIgnoreCaseOrAnnotationContainingIgnoreCase(lang, text, text);
    }

    public Flux<ProductGeneralVariantModel> getProductAttributes(Integer id) {
        return productRepo.getProductAttributes(id);
    }

    public Flux<ProductVariantModel> getProductVariants(Integer id) {
        return productRepo.getProductVariants(id).map(this::convertVariant);
    }

    public Flux<AttributeModel> getProductVariantAttributes(Integer id) {
        return productRepo.getProductVariantAttributes(id);
    }

    public Flux<ProductModel> productsMainPage(@NonNull String lang, @NonNull SortInput sort) {
        Pageable pageable = PageRequest.of(Math.max(sort.getPage() - 1, 0), Math.min(sort.getSize(), 50), Sort.by(Sort.Direction.DESC, "discount").and(Sort.by(Sort.Direction.DESC, "addedDate")));
        return productDetailsRepo.getAllByLangAndAvailable(lang, true, pageable);
    }

    public Flux<ProductModel> productsRandomSearchPage(@NonNull String lang, SortInput sort) {
        int size = sort != null && sort.getSize() != null ? sort.getSize() : 20;
        return productDetailsRepo.randomProducts(lang, Math.min(size, 50));
    }

    private ProductVariantModel convertVariant(ProductModel model) {
        return new ProductVariantModel(model.id(), model.parentId(), model.available(), new PriceModel(model.oldPrice(), model.newPrice(), model.currentPrice(), model.discount(), model.priceFromDate(), model.sale()), model.sku());
    }

    private Pageable convertSort(SortInput sort) {
        if (sort == null) return null;
        Sort.Direction direction = Sort.Direction.fromString(sort.getDirection());
        String field = Objects.equals(sort.getField(), "cost") ? "currentPrice" : "addedDate";

        return PageRequest.of(Math.max(sort.getPage(), 0), Math.min(sort.getSize(), 50), Sort.by(Sort.Direction.DESC, "available").and(Sort.by(direction, field)));
    }

    public Mono<Boolean> inWishList(Integer id, String customerUuid) {
        return customerRepository.getFirstByUuid(customerUuid)
                .flatMap(customer -> wishListRepo.existsByCustomerIdAndProductId(customer.getId(), id))
                .onErrorReturn(false);
    }

    public Mono<Boolean> addToWishList(Integer productId, Boolean flag, String customerUuid) {  //todo why returning null?
        return customerRepository.getFirstByUuid(customerUuid)
                .flatMap(customer -> {
                    if (flag) {
                        return wishListRepo.save(new WishListEntity(productId, customer.getId()));
                    } else {
                        return wishListRepo.deleteAllByCustomerIdAndProductId(customer.getId(), productId);
                    }
                })
                .then(Mono.just(true))
                .defaultIfEmpty(false)
                .onErrorReturn(false);
    }

    public Flux<ProductModel> favourites(String customerUuid, String language) {
        return customerRepository.getFirstByUuid(customerUuid)
                .flatMapMany(customer -> wishListRepo.getFavourites(customer.getId(), language));
    }

    public Mono<Boolean> deleteProduct(Integer id) {
        return productRepo.deleteById(id)
                .thenReturn(true)
                .onErrorResume(ex -> Mono.just(false));
    }

    public Mono<Boolean> saveProduct(InputModel input) {  //todo transactional
        Objects.requireNonNull(input);

        return Mono.justOrEmpty(input.getId())
                .flatMap(id -> productRepo.findById(id)
                        .flatMap(product -> {
                            product.setActive(input.getActive());
                            product.setCategoryId(input.getCategoryId() == -1 ? null : input.getCategoryId());
                            return productRepo.save(product);
                        }))
                .switchIfEmpty(productRepo.save(new ProductEntity())) //todo
                .flatMap(product -> saveTranslations(product.getId(), input.getTranslations()));
    }

    private Mono<Boolean> saveTranslations(Integer id, List<TranslationInputModel> translations) {
        return productTranslationRepo.deleteAllByProductId(id)
                .thenMany(Flux.fromIterable(translations))
                .flatMap(translation -> {
                    ProductTranslationEntity translationEntity = new ProductTranslationEntity(id,
                            translation.getTitle(),
                            translation.getAnnotation(),
                            translation.getDescription(),
                            translation.getLang());
                    return productTranslationRepo.save(translationEntity);
                })
                .then(Mono.just(true))
                .onErrorResume(ex -> Mono.just(false));
    }

    public Mono<Boolean> newProduct() {
        TranslationInputModel defaultTranslation = new TranslationInputModel();
        defaultTranslation.setLang("us");
        defaultTranslation.setTitle("New Product");

        InputModel model = new InputModel();
        model.getTranslations().add(defaultTranslation);

        return saveProduct(model);
    }
}