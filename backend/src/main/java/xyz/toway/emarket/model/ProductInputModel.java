package xyz.toway.emarket.model;

import lombok.Data;

@Data
public class ProductInputModel {
    private Integer id;
    private String title;
    private String annotation;
    private String description;
    private boolean active;
    private Integer categoryId;
}
