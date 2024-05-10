package xyz.toway.emarket.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import xyz.toway.emarket.entity.OrderDataRepository;
import xyz.toway.emarket.entity.ProductTranslationEntity;
import xyz.toway.emarket.model.AdmProductModel;
import xyz.toway.emarket.model.OrderDataItemModel;
import xyz.toway.emarket.model.OrderDataModel;
import xyz.toway.emarket.model.SortInput;
import xyz.toway.emarket.repository.*;

@Service
@AllArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final AdmProductRepository admProductRepository;
    private final ConverterService converterService;
    private final ProductTranslationRepo productTranslationRepo;
    private final OrderDataModelRepository orderDataRepository;
    private final OrderDataItemRepository orderDataItemRepository;

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

    public Flux<OrderDataModel> getOrders(SortInput sort) {
        return orderDataRepository.getAllByIdNotNull(converterService.convertToPageable(sort));
    }

    public Flux<OrderDataItemModel> getOrderItems(Integer id) {
        return orderDataItemRepository.getAllByOrderIdAndLang(id, "us");
    }
}
