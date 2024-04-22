package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

import java.util.Collections;
import java.util.List;

@Data
@Table(name = "user_details", schema = "public")
public class UserDetailsEntity {
    @Id
    @Column("id")
    private Long id;

    @Column("uuid")
    private String uuid;

    @Column("username")
    private String username;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("roles")
    private String rolesRaw;

    @Column("password")
    private String password;

    public List<String> getRoles() {
        return StringUtils.hasText(rolesRaw) ? List.of(rolesRaw.split("\\|")) : Collections.emptyList();
    }

    public boolean isAdmin() {
        return StringUtils.hasText(rolesRaw) && rolesRaw.contains("ROLE_ADMIN");
    }
}
