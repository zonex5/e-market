package xyz.toway.emarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.helper.HttpControllerHelper;
import xyz.toway.emarket.service.ImageService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;
    private final HttpControllerHelper httpHelper;

    @PostMapping("/upload/{productId}")
    private Mono<ResponseEntity<?>> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Integer productId) {
        return Mono.justOrEmpty(file)
                .flatMap(f -> imageService.saveFile(f, productId)
                        .map(savedImage -> ResponseEntity.ok().build())
                        .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body("Failed to upload image."))));
    }

    @GetMapping("/download/{id}")
    public Mono<ResponseEntity<?>> downloadImageById(@PathVariable(name = "id") Integer imageId) {
        return imageService.getFile(imageId)
                .map(file -> ResponseEntity.ok()
                        .headers(httpHelper.createHttpHeader(file.getContentType()))
                        .body(file.getContent())
                );

    }
}
