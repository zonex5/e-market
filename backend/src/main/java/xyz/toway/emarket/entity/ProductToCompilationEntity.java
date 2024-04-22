package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name = "products_to_compilation", schema = "market")
public class ProductToCompilationEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_compilation")
    private Integer compilationId;

    @Column("id_product")
    private Integer productId;

    public ProductToCompilationEntity(Integer productId, Integer compilationId) {
        this.compilationId = compilationId;
        this.productId = productId;
    }
}
