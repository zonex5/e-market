package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;

@Data
@Table(name = "wish_list", schema = "product")
@NoArgsConstructor
public class WishListEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_product")
    private Integer productId;

    @Column("id_customer")
    private Integer customerId;

    public WishListEntity(Integer productId, Integer customerId) {
        this.productId = productId;
        this.customerId = customerId;
    }
}
