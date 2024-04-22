package xyz.toway.emarket.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "category_translation", schema = "market")
public class CategoryTranslationModel extends GeneralTranslation {

    @Column("title")
    String title;
}
