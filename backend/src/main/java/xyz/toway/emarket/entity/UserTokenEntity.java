package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;
import xyz.hamster.tools.annotations.NotInclude;

@Data
@Table(name = "user_tokens", schema = "public")
public class UserTokenEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_user")
    private Integer userId;

    @Column("token")
    private String token;
}
