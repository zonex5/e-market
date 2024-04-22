package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;
import xyz.hamster.tools.annotations.NotInclude;

@Data
@GenerateReactiveRepository(hasActiveFlag = true)
@DataTransferObject
@Table(name = "users", schema = "public")
public class UserEntity {
    @Id
    @Column("id")
    private Long id;

    @Column("username")
    private String username;

    @Column("name")
    private String name;

    @Column("active")
    private boolean active;

    @Column("password")
    @NotInclude
    private String password;
}
