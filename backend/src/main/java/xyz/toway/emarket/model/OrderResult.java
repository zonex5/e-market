package xyz.toway.emarket.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResult {

    private boolean success;
    private List<Integer> unavailableProducts;

    public static OrderResult ok() {
        OrderResult result = new OrderResult();
        result.setSuccess(true);
        return result;
    }

    public static OrderResult fail() {
        OrderResult result = new OrderResult();
        result.setSuccess(false);
        result.setUnavailableProducts(new ArrayList<>());
        return result;
    }

    public OrderResult addUnavailableProducts(List<Integer> ids) {
        unavailableProducts.addAll(ids);
        return this;
    }
}
