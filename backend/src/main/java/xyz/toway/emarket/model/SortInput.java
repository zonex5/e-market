package xyz.toway.emarket.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SortInput {
    private String field;
    private String direction;
    private Integer page;
    private Integer size;

    public SortInput(Integer page, Integer size, String field, String direction) {
        this.field = field;
        this.direction = direction;
        this.page = page;
        this.size = size;
    }

    public SortInput(String field, String direction) {
        this.field = field;
        this.direction = direction;
    }
}


