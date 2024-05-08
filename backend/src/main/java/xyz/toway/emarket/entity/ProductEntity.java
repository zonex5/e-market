package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "products", schema = "product")
public class ProductEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("active")
    private Boolean active;

    @Column("id_category")
    private Integer categoryId;

    @Column("id_parent")
    private Integer parentId;

    @Column("added_date")
    private LocalDateTime creationDate;
}
