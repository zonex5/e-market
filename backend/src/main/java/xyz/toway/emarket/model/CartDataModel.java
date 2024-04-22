package xyz.toway.emarket.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

import java.math.BigDecimal;

@Immutable
@Table(name = "cart_data", schema = "market")
public record CartDataModel(
        @Id
        @Column("id")
        Integer id,

        @Column("id_product")
        Integer productId,

        @Column("id_customer")
        Integer customerId,

        @Column("stock")
        Integer stock,

        @Column("quantity")
        Integer quantity,

        @Column("price")
        BigDecimal price,

        @Column("total_price")
        BigDecimal totalPrice,

        @Column("price_difference")
        BigDecimal priceDifference,

        @Column("available")
        Boolean available
) {
}
