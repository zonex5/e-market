package xyz.toway.emarket.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageModel {
    private String filename;
    private String description;
    private byte[] content;
}
