package xyz.toway.emarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderAmountsModel {
    private BigDecimal subtotal;
    private BigDecimal shipping;
    private BigDecimal total;

    public OrderAmountsModel() {
        subtotal = BigDecimal.ZERO;
        shipping = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
    }
}
