package xyz.toway.emarket.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
public class ProductGeneralVariantModel {

    @Column("id_parent")
    private Integer parentId;

    @Column("id_attribute")
    private Integer id;

    @Column("attribute_name")
    private String name;

    @Column("values")
    private String rowValues;

    public List<String> getValues() {
        return Arrays.stream(Objects.requireNonNullElse(rowValues, "")
                .split("::")).toList();
    }
}
