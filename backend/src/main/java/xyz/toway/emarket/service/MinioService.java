package xyz.toway.emarket.service;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.toway.emarket.helper.MinioFile;

import java.io.InputStream;

@Log4j2
@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    public ObjectWriteResponse uploadToMinio(String bucketName, String objectName, InputStream inputStream, String contentType) {
        try {
            // create bucket if not exists
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, -1, 5242880)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to upload file " + objectName, e);
            throw new RuntimeException(e);
        }
    }

    public ObjectWriteResponse uploadToMinio(String bucketName, String objectName, MultipartFile file) {
        try {
            try (InputStream inputStream = file.getInputStream()) {
                return uploadToMinio(bucketName, objectName, inputStream, file.getContentType());
            }
        } catch (Exception e) {
            log.error("Failed to upload file " + objectName, e);
            throw new RuntimeException(e);
        }
    }

    public MinioFile downloadFromMinio(String bucketName, String objectName) {
        try {
            var content = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            ).readAllBytes();
            return new MinioFile(content);
        } catch (Exception e) {
            log.error("Failed to download file " + objectName, e);
            return new MinioFile(new byte[0]);
        }
    }
}
