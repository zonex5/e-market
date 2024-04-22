package xyz.toway.emarket.helper.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW("new"),
    PROCESSING("processing"),
    SUSPENDED("suspended"),
    DONE("done");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
