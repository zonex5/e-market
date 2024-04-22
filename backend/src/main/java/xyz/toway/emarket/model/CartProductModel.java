package xyz.toway.emarket.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Immutable
@Table(name = "cart_products", schema = "market")
public record CartProductModel(
        @Id
        @Column("id")
        Integer id,

        @Column("title")
        String title,

        @Column("active")
        Boolean active,

        @Column("available")
        Boolean available,

        @Column("quantity")
        Integer quantity,

        @Column("old_price")
        BigDecimal oldPrice,

        @Column("current_price")
        BigDecimal currentPrice,

        @Column("sale")
        Boolean sale,

        @Column("lang")
        String lang,

        @Column("uuid_customer")
        String customerUuid,

        @Column("id_customer")
        Integer customerId,

        @Column("thumbnail")
        Integer thumbnail,

        @Column("details")
        String details,

        @Column("discount")
        Integer discount
) {
}
