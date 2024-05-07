package xyz.toway.emarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.toway.emarket.model.GeneralTranslation;

@Data
@DataTransferObject(nameSuffix = "Model", builder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "category_translation", schema = "market")
public class CategoryTranslationEntity extends GeneralTranslation {

    @Column("id_category")
    private Integer categoryId;

    @Column("title")
    private String title;

    public CategoryTranslationEntity(Integer categoryId, String title, String lang) {
        this.categoryId = categoryId;
        this.title = title;
        setLang(lang);
    }
}
