package com.notice.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    String upload(String imageKey, MultipartFile image);
}