package xyz.toway.emarket.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Immutable
@Table(name = "products_details", schema = "market")
public record ProductModel(
        @Id
        @Column("id")
        Integer id,

        @Column("sku")
        String sku,

        @Column("active")
        Boolean active,

        @Column("id_category")
        Integer categoryId,

        @Column("id_parent")
        Integer parentId,

        @Column("title")
        String title,

        @Column("annotation")
        String annotation,

        @Column("description")
        String description,

        @Column("lang")
        String lang,

        @Column("stock")
        Integer stock,

        @Column("id_price")
        Integer priceId,

        @Column("old_price")
        BigDecimal oldPrice,

        @Column("new_price")
        BigDecimal newPrice,

        @Column("current_price")
        BigDecimal currentPrice,

        @Column("discount")
        Integer discount,

        @Column("price_from_date")
        LocalDateTime priceFromDate,

        @Column("sale")
        Boolean sale,

        @Column("available")
        Boolean available,

        @Column("added_date")
        LocalDateTime addedDate,

        @Column("thumbnail")
        String thumbnail
) {
}
