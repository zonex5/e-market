package xyz.toway.emarket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class GeneralTranslation {
    @Id
    @Column("id")
    private Integer id;

    @Column("lang")
    private String lang;
}
