package xyz.toway.emarket.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class InputModel {
    private Integer id;
    private Boolean active;
    private List<TranslationInputModel> translations;

    public InputModel() {
        this.active = false;
        translations = new ArrayList<>();
    }
}