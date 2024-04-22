package xyz.toway.emarket.model;

import org.springframework.data.relational.core.mapping.Column;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceModel(
        @Column("old_price")
        BigDecimal oldPrice,
        @Column("new_price")
        BigDecimal newPrice,
        @Transient
        BigDecimal currentPrice,
        Integer discount,
        @Column("from_date")
        LocalDateTime fromDate,
        Boolean sale
) {
}
