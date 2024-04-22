package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@GenerateReactiveRepository
@DataTransferObject
@Table(name = "roles", schema = "public")
public class RoleEntity {
    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;
}
