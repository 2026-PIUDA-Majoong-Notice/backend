package com.notice.controller.image;

import com.notice.dto.image.PostImageResponse;
import com.notice.service.image.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final PostImageService postImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostImageResponse> postImage(
            @RequestParam("image") MultipartFile image
    ) {
        PostImageResponse response = postImageService.execute(image);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}