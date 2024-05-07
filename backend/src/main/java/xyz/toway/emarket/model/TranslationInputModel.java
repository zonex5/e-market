package xyz.toway.emarket.model;

import lombok.Data;

@Data
public class TranslationInputModel {
    private Integer id;
    private String lang;
    private String title;
    private String annotation;
    private String description;
}
