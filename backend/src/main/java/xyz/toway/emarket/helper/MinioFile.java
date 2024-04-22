package xyz.toway.emarket.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinioFile {
    private Integer id;
    private String name;
    private String contentType;
    private byte[] content;

    public MinioFile(byte[] content) {
        this.content = content;
    }
}
