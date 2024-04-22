package xyz.toway.emarket.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import xyz.toway.emarket.entity.CategoryEntity;
import xyz.toway.emarket.entity.CompilationEntity;
import xyz.toway.emarket.entity.ProductEntity;
import xyz.toway.emarket.model.*;

import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public final class ConverterService {
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

/*    public ProductModel productToModel(ProductEntity entity) {
        return ProductModel.builder()
                .active(entity.getActive())
                .description(entity.getDescriptionUuid())  //todo
                .annotation(entity.getAnnotationUuid())
                .title(entity.getTitleUuid())
                .id(entity.getId())
                .pictureId(entity.getPictureId())
                .build();

       return new ProductModel(entity.getId(), )
    }*/

/*    public PriceModel priceToModel(ProductPriceEntity entity) {
        return PriceModel.builder()
                .originalPrice(entity.getOriginalPrice().doubleValue())
                .discount(entity.getDiscount())
                .discountPrice(entity.getDiscountPrice().doubleValue())
                .fromDate(entity.getFromDate().format(dateFormatter))
                .build();
    }

    public ProductPriceEntity priceToEntity(PriceInput priceInput) {
        ProductPriceEntity entity = new ProductPriceEntity();
        entity.setOriginalPrice(BigDecimal.valueOf(priceInput.getOriginalPrice()));
        entity.setDiscount(priceInput.getDiscount());
        entity.setFromDate(priceInput.getFromDate() != null ? LocalDateTime.parse(priceInput.getFromDate(), dateFormatter) : LocalDateTime.now());
        entity.setIdProduct(priceInput.getProductId());
        entity.setDiscountPrice(BigDecimal.valueOf(calculateDiscountPrice(priceInput.getOriginalPrice(), priceInput.getDiscount())));
        return entity;
    }*/

    public CategoryModel categoryToModel(CategoryEntity entity) {
        /*return CategoryModel.builder()
                .id(entity.getId())
                //.title(entity.getTitleUuid()) //todo
                //.annotation(entity.getAnnotationUuid())
                .active(entity.getActive())
                .pictureId(entity.getPictureId())
                .build();*/
        return null;
    }

    public CategoryEntity categoryToEntity(CategoryInputModel input) {
        /*CategoryEntity entity = new CategoryEntity();
        entity.setActive(Objects.requireNonNullElse(input.getActive(), true));
        entity.setTitleUuid(input.getTitle());  //todo
        entity.setAnnotationUuid(input.getAnnotation());
        return entity;*/
        return null;
    }

    public ProductEntity productToEntity(ProductInputModel productInput) {
        ProductEntity entity = new ProductEntity();
        entity.setActive(productInput.isActive());
        entity.setAnnotationUuid(productInput.getAnnotation());
        entity.setDescriptionUuid(productInput.getDescription());
        entity.setTitleUuid(productInput.getTitle());
        entity.setCategoryId(productInput.getCategoryId());
        return entity;
    }

    public CompilationModel compilationToModel(CompilationEntity entity) {
        return CompilationModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .annotation(entity.getAnnotation())
                .active(entity.getActive())
                .background(entity.getBackground())
                .build();
    }

    public CompilationEntity compilationToEntity(CompilationInputModel input) {
        CompilationEntity entity = new CompilationEntity();
        entity.setActive(input.getActive());
        entity.setTitle(input.getTitle());
        entity.setAnnotation(input.getAnnotation());
        entity.setBackground(input.getBackground());
        return entity;
    }

    private Double calculateDiscountPrice(Double originalPrice, Integer discount) {
        if (discount == null || originalPrice == null) {
            return 0D;
        }
        if (discount < 0 || discount > 100) {
            return originalPrice;
        }
        double discountedPrice = originalPrice * (1 - discount / 100.0);
        discountedPrice = Math.round(discountedPrice * 100.0) / 100.0;
        return discountedPrice;
    }
}

