package com.notice.domain.log;

import com.notice.domain.elder.Elder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "meal_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MealLog {

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
    private MealAmount mealAmount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private MealLog(Elder elder, LocalDateTime recordedAt, MealAmount mealAmount) {
        this.elder = elder;
        this.recordedAt = recordedAt;
        this.mealAmount = mealAmount;
        this.createdAt = LocalDateTime.now();
    }

    public static MealLog create(Elder elder, LocalDateTime recordedAt, MealAmount mealAmount) {
        return new MealLog(elder, recordedAt, mealAmount);
    }
}