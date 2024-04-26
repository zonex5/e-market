package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.NotInclude;

@Data
@NoArgsConstructor
@Table(name = "users", schema = "public")
public class UserEntity{

    @Id
    @Column("id")
    private Integer id;

    @Column("username")
    private String username;

    @Column("name")
    private String name;

    @Column("active")
    private boolean active;

    @Column("password")
    @NotInclude
    private String password;

    public UserEntity(String username, String password, boolean active) {
        this.username = username;
        this.active = active;
        this.password = password;
    }
}
