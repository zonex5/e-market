package xyz.toway.emarket.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ImageEntity;
import xyz.toway.emarket.model.ImageModel;

public interface IImageService {
    Mono<ImageEntity> saveImage(MultipartFile file);
    Mono<ImageModel> getImage(Integer imageId);
}
