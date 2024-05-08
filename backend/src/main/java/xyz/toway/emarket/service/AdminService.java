package xyz.toway.emarket.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.entity.ProductTranslationEntity;
import xyz.toway.emarket.model.AdmProductModel;
import xyz.toway.emarket.model.SortInput;
import xyz.toway.emarket.repository.AdmProductRepository;
import xyz.toway.emarket.repository.ProductRepository;
import xyz.toway.emarket.repository.ProductTranslationRepo;

@Service
@AllArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final AdmProductRepository admProductRepository;
    private final ConverterService converterService;
    private final ProductTranslationRepo productTranslationRepo;

    public Flux<AdmProductModel> getAllProducts(SortInput sort) {
        return admProductRepository.findByIdNotNull(converterService.convertToPageable(sort));
    }

    public Flux<AdmProductModel> getAllProductsByCategory(Integer categoryId, SortInput sort) {
        return admProductRepository.findByCategoryId(categoryId, converterService.convertToPageable(sort));
    }

    public Flux<ProductTranslationEntity> getProductTranslations(Integer productId) {
        return productTranslationRepo.getAllByProductId(productId);
    }

    public Flux<AdmProductModel> getProductsWithoutCategory(SortInput sort) {
        return admProductRepository.findByCategoryIdIsNull(converterService.convertToPageable(sort));
    }
}
