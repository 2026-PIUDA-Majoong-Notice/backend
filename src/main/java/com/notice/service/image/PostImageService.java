package com.notice.service.image;

import com.notice.domain.image.Image;
import com.notice.dto.image.PostImageResponse;
import com.notice.exception.HttpException;
import com.notice.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final ImageStorageService imageStorageService;
    private final ImageRepository imageRepository;

    public PostImageResponse execute(MultipartFile image) {
        validateImage(image);

        String imageKey = createImageKey(image.getOriginalFilename());
        String imageUrl = imageStorageService.upload(imageKey, image);

        Image savedImage = imageRepository.save(
                Image.create(imageKey, imageUrl)
        );

        return new PostImageResponse(savedImage.getId());
    }

    private void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new HttpException.BadRequest(
                    "body[image] must not be empty"
            );
        }

        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new HttpException.BadRequest(
                    "body[image] must be less than 5MB"
            );
        }

        String contentType = image.getContentType();

        if (contentType == null || ALLOWED_CONTENT_TYPES.contains(contentType) == false) {
            throw new HttpException.BadRequest(
                    "body[image] must be jpg, png, or webp"
            );
        }

        if (hasInvalidExtension(image.getOriginalFilename())) {
            throw new HttpException.BadRequest(
                    "body[image] must have extension"
            );
        }
    }

    private boolean hasInvalidExtension(String filename) {
        return filename == null || filename.contains(".") == false;
    }

    private String createImageKey(String originalFilename) {
        String extension = getExtension(originalFilename);

        return "images/" + UUID.randomUUID() + extension;
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }
}