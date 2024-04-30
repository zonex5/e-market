package xyz.toway.emarket.helper.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW("new"),
    SHIPPED("shipped"),
    COMPLETED("completed"),

    // used only by filter
    ALL("all"),
    ACTIVE("active");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
