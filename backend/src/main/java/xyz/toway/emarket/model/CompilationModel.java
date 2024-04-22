package xyz.toway.emarket.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompilationModel {
    private Integer id;
    private String title;
    private String annotation;
    private Boolean active;
    private String background;
}
