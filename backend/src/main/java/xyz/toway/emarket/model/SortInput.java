package xyz.toway.emarket.model;

public record SortInput(
        String field,
        String direction,
        Integer page,
        Integer size
) {
}
