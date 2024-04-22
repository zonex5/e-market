package xyz.toway.emarket.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShippingMethodModel {
    private String methodName;
    private BigDecimal cost;
}
