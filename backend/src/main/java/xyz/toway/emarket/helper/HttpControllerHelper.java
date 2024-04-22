package xyz.toway.emarket.helper;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HttpControllerHelper {
    public MediaType getMediaTypeByExtension(@NonNull String extension) {
        switch (extension.toLowerCase()) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "webp":
                return MediaType.parseMediaType("image/webp");
            default:
                return MediaType.IMAGE_JPEG;
        }
    }

    public HttpHeaders createHttpHeader(String contentType) {
        HttpHeaders headers = new HttpHeaders();
        if (contentType != null) {
            headers.setContentType(MediaType.parseMediaType(contentType));
        }
        return headers;
    }
}
