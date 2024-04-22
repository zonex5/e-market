package xyz.toway.emarket.model;

public record CategoryModel(
        Integer id,
        String title, //todo replace with CategoryTranslation
        Boolean active
) {
}
