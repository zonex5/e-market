package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@GenerateReactiveRepository
@Table(name = "order_status_history", schema = "customer")
public class OrderStatusHistoryEntity {

    @Id
    @Column("id")
    private Integer id;

    @Column("id_order")
    private Integer orderId;

    @Column("status")
    private String status;

    @Column("date_of_change")
    private LocalDateTime dateOfChange;
}
