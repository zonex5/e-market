package xyz.toway.emarket.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Immutable
@Table(name = "products_view", schema = "admin")
public record AdmProductModel(
        @Id
        @Column("id")
        Integer id,

        @Column("active")
        Boolean active,

        @Column("id_category")
        Integer categoryId,

        @Column("category_title")
        String categoryTitle,

        @Column("title")
        String title
) {
}
