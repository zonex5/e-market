package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.toway.emarket.model.GeneralTranslation;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "product_translations", schema = "product")
public class ProductTranslationEntity extends GeneralTranslation {

    @Column("id_product")
    private Integer productId;

    @Column("title")
    private String title;

    @Column("annotation")
    private String annotation;

    @Column("description")
    private String description;

    public ProductTranslationEntity(Integer productId, String title, String annotation, String description, String lang) {
        this.productId = productId;
        this.title = title;
        this.annotation = annotation;
        this.description = description;
        setLang(lang);
    }
}
