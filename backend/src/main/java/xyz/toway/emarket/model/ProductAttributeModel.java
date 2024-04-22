package xyz.toway.emarket.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class ProductAttributeModel {
    @Column("id")
    private Integer id;

    @Column("id_product")
    private Integer productId;

    @Column("id_parent")
    private Integer parentId;

    @Column("id_attribute")
    private Integer attributeId;

    @Column("attribute_name")
    private String name;
}
