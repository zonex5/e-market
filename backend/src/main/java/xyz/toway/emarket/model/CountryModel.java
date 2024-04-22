package xyz.toway.emarket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@GenerateReactiveRepository
@Table(name = "countries", schema = "public")
public class CountryModel {
    @Id
    @Column("id")
    private Integer id;

    @Column("name")
    private String name;

    @Column("code")
    private String code;

    @Column("phone")
    private String phone;
}
