package xyz.toway.emarket.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@Table(name = "customers", schema = "customer")
@DataTransferObject
public class CustomerEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_user")
    private Integer userId;

    @Column("uuid")
    private String uuid;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("phone")
    private String phone;

    @Column("email")
    private String email;

}
