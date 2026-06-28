package com.notice.domain.log;

import com.notice.domain.elder.Elder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "sleep_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SleepLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 어르신의 수면 기록인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_id", nullable = false)
    private Elder elder;

    // 기상 시간, 선택값
    private LocalDateTime wakeTime;

    // 취침 시간, 선택값
    private LocalDateTime sleepTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private SleepLog(
            Elder elder,
            LocalDateTime wakeTime,
            LocalDateTime sleepTime
    ) {
        this.elder = elder;
        this.wakeTime = wakeTime;
        this.sleepTime = sleepTime;
        this.createdAt = LocalDateTime.now();
    }

    public static SleepLog create(
            Elder elder,
            LocalDateTime wakeTime,
            LocalDateTime sleepTime
    ) {
        return new SleepLog(
                elder,
                wakeTime,
                sleepTime
        );
    }
}