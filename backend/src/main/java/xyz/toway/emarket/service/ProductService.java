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
import xyz.toway.emarket.entity.CompilationRepository;
import xyz.toway.emarket.entity.WishListEntity;
import xyz.toway.emarket.model.*;
import xyz.toway.emarket.repository.*;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final ProductImageRepo productImageRepo;
    private final ProductPriceRepo productPriceRepo;
    private final CompilationRepository compilationRepo;
    private final ProductsToCompilationRepository productsToCompilationRepository;
    private final StringRepository stringRepository;
    private final ConverterService converter;
    private final ImageRepository imageRepository;
    private final ProductDetailsRepository productDetailsRepo;
    private final WishListRepo wishListRepo;
    private final CustomerRepository customerRepository;

    public Flux<ProductModel> getAllProducts(String lang) {
        return productRepo.getAll(lang, null);
    }

    public Flux<ProductModel> getActiveProducts(String lang) {
        return productRepo.getAllByActive(true, lang, null);
    }

    public Mono<ProductModel> getProductById(Integer id, String lang) {
        Objects.requireNonNull(id);
        return productRepo.getById(id, lang);
    }

    public Flux<ProductModel> getAllOrphans(String lang) {
        return productRepo.getAllOrphans(lang, null);
    }

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
        Pageable pageable = PageRequest.of(Math.max(sort.page() - 1, 0), Math.min(sort.size(), 50), Sort.by(Sort.Direction.DESC, "discount").and(Sort.by(Sort.Direction.DESC, "addedDate")));
        return productDetailsRepo.getAllByLangAndAvailable(lang, true, pageable);
    }

    public Flux<ProductModel> productsRandomSearchPage(@NonNull String lang, SortInput sort) {
        int size = sort != null && sort.size() != null ? sort.size() : 20;
        return productDetailsRepo.randomProducts(lang, Math.min(size, 50));
    }

    public Mono<Integer> totalProductsMainPage(String lang) {
        return productDetailsRepo.countAllByLangAndAvailable(lang, true);
    }

    private ProductVariantModel convertVariant(ProductModel model) {
        return new ProductVariantModel(model.id(), model.parentId(), model.available(), new PriceModel(model.oldPrice(), model.newPrice(), model.currentPrice(), model.discount(), model.priceFromDate(), model.sale()), model.sku());
    }

    private Pageable convertSort(SortInput sort) {
        if (sort == null) return null;
        Sort.Direction direction = Sort.Direction.fromString(sort.direction());
        String field = Objects.equals(sort.field(), "cost") ? "currentPrice" : "addedDate";

        return PageRequest.of(Math.max(sort.page(), 0), Math.min(sort.size(), 50), Sort.by(Sort.Direction.DESC, "available").and(Sort.by(direction, field)));
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
}