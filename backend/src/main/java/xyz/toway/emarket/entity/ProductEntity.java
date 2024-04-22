package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "product", schema = "market")
public class ProductEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("uuid_title")
    private String titleUuid;

    @Column("uuid_annotation")
    private String annotationUuid;

    @Column("uuid_description")
    private String descriptionUuid;

    @Column("active")
    private Boolean active;

    @Column("id_picture")
    private Integer pictureId;;

    @Column("id_category")
    private Integer categoryId;
}
