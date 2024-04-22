package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;

@Data
@DataTransferObject
@Table(name = "user_to_roles", schema = "public")
public class UserToRoleEntity {
    @Id
    @Column("id")
    private Long id;

    @Column("id_user")
    private Long userId;

    @Column("id_role")
    private Long roleId;
}
