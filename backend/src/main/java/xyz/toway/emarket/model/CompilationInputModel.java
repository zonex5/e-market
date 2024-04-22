package xyz.toway.emarket.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompilationInputModel {
    private Integer id;
    private String title;
    private String annotation;
    private Boolean active;
    private String background;
    private List<Integer> products;

    public CompilationInputModel() {
        products = new ArrayList<>();
    }
}
