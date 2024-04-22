package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "image_to_product", schema = "market")
public class ImageToProductEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_product")
    private Integer productId;

    @Column("id_image")
    private Integer imageId;
}
