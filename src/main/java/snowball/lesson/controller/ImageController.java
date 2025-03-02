package snowball.lesson.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import snowball.lesson.dto.ApiResponse;
import snowball.lesson.service.S3ImageService;

@Slf4j
@Tag(name = "이미지 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
public class ImageController {
    private final S3ImageService s3ImageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<String> uploadImage(@RequestPart MultipartFile file) {
        String imgPath = s3ImageService.upload(file);
        log.info("imagePath = {}", imgPath);
        return ApiResponse.success(imgPath);
    }

    @DeleteMapping
    public ApiResponse<String> deleteImage(@RequestParam String url) {
        s3ImageService.deleteByUrl(url);
        return ApiResponse.success();
    }
}
