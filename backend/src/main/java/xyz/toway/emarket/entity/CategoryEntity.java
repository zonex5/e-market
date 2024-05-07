package xyz.toway.emarket.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "category", schema = "market")
public class CategoryEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("active")
    private Boolean active;

    public CategoryEntity() {
        this.active = false;
    }
}
