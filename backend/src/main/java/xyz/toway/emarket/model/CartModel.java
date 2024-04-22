package xyz.toway.emarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartModel {
    List<CartProductModel> products;
    BigDecimal totalAmount;
    BigDecimal savedAmount;

    public CartModel() {
        products = new ArrayList<>();
        totalAmount = BigDecimal.ZERO;
        savedAmount = BigDecimal.ZERO;
    }
}
