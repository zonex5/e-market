package xyz.toway.emarket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table(name = "order_items_view", schema = "customer")
public class OrderDataItemModel {

    @Id
    @Column("id_product")
    private Integer productId;

    @Column("id_order")
    private Integer orderId;

    @Column("price")
    private BigDecimal price;

    @Column("quantity")
    private Integer quantity;

    @Column("title")
    private String productName;

    @Column("lang")
    private String lang;

    @Column("details")
    private String details;

    @Column("thumbnail")
    private Integer thumbnail;
}
