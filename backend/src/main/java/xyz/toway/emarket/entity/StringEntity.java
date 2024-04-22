package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.DataTransferObject;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@NoArgsConstructor
@Table(name = "strings", schema = "storage")
@DataTransferObject
public class StringEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("lang")
    private String lang;

    @Column("value")
    private String value;

    /**
     * Related entity ID
     */
    @Column("item_uuid")
    private String itemKey;

    @Column("field_name")
    private String fieldName;

    public StringEntity(String value) {
        this.value = value;
    }
}
