package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;
import xyz.toway.emarket.helper.enums.OrderStatus;

@Data
@NoArgsConstructor
@GenerateReactiveRepository
@Table(name = "orders", schema = "customer")
public class OrderEntity {

    @Id
    @Column("id")
    private Integer id;

    @Column("id_customer")
    private Integer customerId;

    @Column("status")
    private String status;

    @Column("hidden")
    private Boolean hidden;

    public static OrderEntity createNewOrder() {
        OrderEntity entity = new OrderEntity();
        entity.setStatus(OrderStatus.NEW.getValue());
        entity.setHidden(false);
        return entity;
    }
}
