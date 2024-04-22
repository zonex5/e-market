package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@NoArgsConstructor
@GenerateReactiveRepository
@Table(name = "order_data", schema = "customer")
public class OrderDataEntity {

    @Id
    @Column("id")
    private Integer id;

    @Column("id_order")
    private Integer orderId;

    @Column("id_country")
    private Integer countryId;

    @Column("address")
    private String address;

    @Column("zip")
    private String zip;

    @Column("city")
    private String city;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("phone")
    private String phone;
}
