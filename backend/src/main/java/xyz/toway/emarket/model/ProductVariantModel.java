package xyz.toway.emarket.model;

public record ProductVariantModel (
 Integer productId,
 Integer parentId,
 Boolean inStock,
 PriceModel price,
 String sku
    ) {}
