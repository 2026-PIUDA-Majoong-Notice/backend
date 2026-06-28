package com.notice.domain.image;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_key", nullable = false, length = 500)
    private String imageKey;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Image(String imageKey, String imageUrl) {
        this.imageKey = imageKey;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
    }

    public static Image create(String imageKey, String imageUrl) {
        return new Image(imageKey, imageUrl);
    }
}