package com.notice.domain.log;

import com.notice.domain.elder.Elder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "hydration_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HydrationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_id", nullable = false)
    private Elder elder;

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HydrationAmount hydrationAmount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private HydrationLog(
            Elder elder,
            LocalDateTime recordedAt,
            HydrationAmount hydrationAmount
    ) {
        this.elder = elder;
        this.recordedAt = recordedAt;
        this.hydrationAmount = hydrationAmount;
        this.createdAt = LocalDateTime.now();
    }

    public static HydrationLog create(
            Elder elder,
            LocalDateTime recordedAt,
            HydrationAmount hydrationAmount
    ) {
        return new HydrationLog(elder, recordedAt, hydrationAmount);
    }
}