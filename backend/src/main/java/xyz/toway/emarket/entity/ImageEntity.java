package xyz.toway.emarket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import xyz.hamster.tools.annotations.GenerateReactiveRepository;

@Data
@NoArgsConstructor
@Table(name = "images", schema = "storage")
public class ImageEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("filename")
    private String filename;

    @Column("content_type")
    private String contentType;

    public ImageEntity(String filename, String contentType) {
        this.filename = filename;
        this.contentType = contentType;
    }
}
