package xyz.toway.emarket.model;

import lombok.Data;

@Data
public class CategoryInputModel {
    private Integer id;
    private String title;
    private String annotation;
    private Boolean active;
}