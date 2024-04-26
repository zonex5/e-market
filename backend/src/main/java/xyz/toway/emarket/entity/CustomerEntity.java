package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@Table(name = "customers", schema = "customer")
@DataTransferObject
@NoArgsConstructor
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

    public CustomerEntity(Integer userId, String uuid, String firstName, String lastName, String phone, String email) {
        this.userId = userId;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
}
