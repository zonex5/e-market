package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;

@Data
@Table(name = "cart", schema = "market")
@DataTransferObject
@NoArgsConstructor
public class CartEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_product")
    private Integer productId;

    @Column("quantity")
    private Integer quantity;

    @Column("id_customer")
    private Integer customerId;

    public CartEntity(Integer productId, Integer quantity, Integer customerId) {
        this.productId = productId;
        this.quantity = quantity;
        this.customerId = customerId;
    }
}
