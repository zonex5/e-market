package xyz.toway.emarket.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table(name = "order_data_view", schema = "customer")
public class OrderDataModel {

    @Id
    @Column("id")
    private Integer id;

    @Column("status")
    private String status;

    @Column("order_date")
    private LocalDate orderDate;

    @Column("id_customer")
    private Integer customerId;

    @Column("customer_uuid")
    private String customerUuid;
}
