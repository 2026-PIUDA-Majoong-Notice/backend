package com.notice.service.image;

import com.notice.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ImageStorageService implements ImageStorageService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(String imageKey, MultipartFile image) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(imageKey)
                    .contentType(image.getContentType())
                    .contentLength(image.getSize())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(image.getInputStream(), image.getSize())
            );

            return getImageUrl(imageKey);
        } catch (IOException exception) {
            throw new HttpException.BadRequest(
                    "body[image] must be valid"
            );
        } catch (S3Exception exception) {
            throw new HttpException.BadRequest(
                    "body[image] upload failed: " + exception.awsErrorDetails().errorMessage()
            );
        }
    }

    private String getImageUrl(String imageKey) {
        return s3Client.utilities()
                .getUrl(GetUrlRequest.builder()
                        .bucket(bucket)
                        .key(imageKey)
                        .build())
                .toString();
    }
}