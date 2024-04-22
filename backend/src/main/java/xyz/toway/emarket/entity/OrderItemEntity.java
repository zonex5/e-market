package xyz.toway.emarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GenerateReactiveRepository
@Table(name = "order_items", schema = "customer")
public class OrderItemEntity {

    @Id
    @Column("id")
    private Integer id;

    @Column("id_order")
    private Integer orderId;

    @Column("id_product")
    private Integer productId;

    @Column("price")
    private BigDecimal price;

    @Column("quantity")
    private Integer quantity;
}
