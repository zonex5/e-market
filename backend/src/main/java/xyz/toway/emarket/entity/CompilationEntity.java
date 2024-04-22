package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@GenerateReactiveRepository(hasActiveFlag = true)
@Table(name = "compilation", schema = "market")
public class CompilationEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("uuid_title")
    private String title;

    @Column("uuid_annotation")
    private String annotation;

    @Column("active")
    private Boolean active;

    @Column("background")
    private String background;
}
