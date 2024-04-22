package xyz.toway.emarket.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@GenerateReactiveRepository()
@Table(name = "languages", schema = "public")
public class LanguageModel {

    @Column("lang")
    private String code;

    @Column("name")
    private String name;

    @Column("is_default")
    private Boolean isDefault;
}
