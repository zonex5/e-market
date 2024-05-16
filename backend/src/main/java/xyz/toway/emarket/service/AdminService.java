package xyz.toway.emarket.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ImageToProductEntity;
import xyz.toway.emarket.entity.OrderRepository;
import xyz.toway.emarket.entity.ProductTranslationEntity;
import xyz.toway.emarket.helper.enums.OrderStatus;
import xyz.toway.emarket.model.AdmProductModel;
import xyz.toway.emarket.model.OrderDataItemModel;
import xyz.toway.emarket.model.OrderDataModel;
import xyz.toway.emarket.model.SortInput;
import xyz.toway.emarket.repository.*;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final AdmProductRepository admProductRepository;
    private final ConverterService converterService;
    private final ProductTranslationRepo productTranslationRepo;
    private final OrderDataModelRepository orderDataRepository;
    private final OrderDataItemRepository orderDataItemRepository;
    private final OrderRepository orderRepository;
    private final ProductImageRepo productImageRepo;
    private final ImageRepository imageRepository;

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

    public Mono<AdmProductModel> getProductById(Integer id) {
        return admProductRepository.findById(id);
    }

    public Flux<OrderDataModel> getOrders(SortInput sort) {
        return orderDataRepository.getAllByIdNotNull(converterService.convertToPageable(sort));
    }

    public Flux<OrderDataItemModel> getOrderItems(Integer id) {
        return orderDataItemRepository.getAllByOrderIdAndLang(id, "us");
    }

    public Mono<Boolean> setOrderStatus(Integer id, String status) {
        return orderRepository.findById(id)
                .flatMap(order -> {
                    OrderStatus orderStatus = OrderStatus.fromString(status);
                    order.setStatus(orderStatus.getValue());
                    return orderRepository.save(order);
                })
                .thenReturn(true)
                .onErrorReturn(false);
    }

    public Flux<Integer> getProductImages(Integer id) {
        Objects.requireNonNull(id);
        return productImageRepo.getAllByProductIdOrderByImageId(id).map(ImageToProductEntity::getImageId);
    }

    public Mono<Boolean> deleteImage(Integer id) {
        Objects.requireNonNull(id);
        return imageRepository.deleteById(id).thenReturn(true);
    }

    public Mono<Boolean> updateThumbnail(Integer imageId, Integer productId) {
        return productImageRepo.removeThumbnailsMyProduct(productId)
                .then(Mono.defer(() -> productImageRepo.setThumbnailByProductId(imageId, productId)))
                .thenReturn(true)
                .onErrorReturn(false);
    }
}
