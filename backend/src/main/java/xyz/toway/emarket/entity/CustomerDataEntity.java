package xyz.toway.emarket.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Getter
@Setter
@DataTransferObject(nameSuffix = "Model", builder = true)
@Table(name = "customer_data", schema = "customer")
public class CustomerDataEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("id_customer")
    private Integer customerId;

    @Column("id_country")
    private Integer countryId;

    @Column("address")
    private String address;

    @Column("zip")
    private String zip;

    @Column("city")
    private String city;

    @Column("save")
    private Boolean save;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone")
    private String phone;
}
