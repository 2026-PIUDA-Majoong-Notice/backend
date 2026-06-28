package com.notice.domain.log;

import com.notice.domain.elder.Elder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "toilet_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToiletLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_id", nullable = false)
    private Elder elder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ToiletType toiletType;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ToiletLog(
            Elder elder,
            ToiletType toiletType,
            LocalDateTime recordedAt
    ) {
        this.elder = elder;
        this.toiletType = toiletType;
        this.recordedAt = recordedAt;
        this.createdAt = LocalDateTime.now();
    }

    public static ToiletLog create(
            Elder elder,
            ToiletType toiletType,
            LocalDateTime recordedAt
    ) {
        return new ToiletLog(
                elder,
                toiletType,
                recordedAt
        );
    }
}