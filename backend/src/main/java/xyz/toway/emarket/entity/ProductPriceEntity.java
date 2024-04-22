package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "product_price", schema = "market")
public class ProductPriceEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_product")
    private Integer idProduct;

    @Column("old_price")
    private BigDecimal oldPrice;

    @Column("new_price")
    private BigDecimal newPrice;

    @Column("discount")
    private Integer discount;

    @Column("from_date")
    private LocalDateTime fromDate;

    public ProductPriceEntity() {
        oldPrice = BigDecimal.ZERO;
        newPrice = BigDecimal.ZERO;
    }
}
