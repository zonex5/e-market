package xyz.toway.emarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.ImageEntity;
import xyz.toway.emarket.entity.ImageToProductEntity;
import xyz.toway.emarket.helper.MinioFile;
import xyz.toway.emarket.repository.ImageRepository;
import xyz.toway.emarket.repository.ProductImageRepo;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.images-market-bucket}")
    private String imagesMarketBucket;

    private final MinioService minioService;
    private final ImageRepository imageRepository;
    private final ProductImageRepo productImageRepo;

    @Transactional
    public Mono<?> saveFile(MultipartFile file, Integer productId) {
        Objects.requireNonNull(file, "MultipartFile is null.");

        var uuid = UUID.randomUUID().toString();
        var filename = Optional.ofNullable(file.getOriginalFilename()).map(StringUtils::getFilenameExtension).map(ext -> "." + ext.toLowerCase()).map(ext -> uuid + ext).orElse(uuid);

        return saveToMinio(file, filename)
                .flatMap(obj -> imageRepository.save(new ImageEntity(filename, file.getContentType())))
                .flatMap(img -> productImageRepo.save(new ImageToProductEntity(productId, img.getId())));
    }

    public Mono<MinioFile> getFile(Integer imageId) {
        return imageRepository.findById(imageId)
                .flatMap(img -> getFromMinio(img.getFilename())
                        .map(file -> new MinioFile(img.getId(), img.getFilename(), img.getContentType(), file.getContent()))
                );
    }

    private Mono<MinioFile> getFromMinio(String filename) {
        var downloadResult = CompletableFuture.supplyAsync(() -> minioService.downloadFromMinio(imagesMarketBucket, filename));
        return Mono.fromFuture(downloadResult);
    }

    private Mono<?> saveToMinio(MultipartFile file, String filename) {
        var uploadResult = CompletableFuture.supplyAsync(() -> minioService.uploadToMinio(imagesMarketBucket, filename, file));
        return Mono.fromFuture(uploadResult);
    }
}
