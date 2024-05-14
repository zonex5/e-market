package xyz.toway.emarket.helper.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW("new"),
    SHIPPED("shipped"),
    COMPLETED("completed"),
    CANCELED("canceled"),
    PENDING("pending"),

    // used only by filter
    ALL("all"),
    ACTIVE("active");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static OrderStatus fromString(String text) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.value.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
