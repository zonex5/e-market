package xyz.toway.emarket.model;

import lombok.Data;

@Data
public class PriceInput {
    Integer productId;
    Double originalPrice;
    Integer discount;
    String fromDate;
}