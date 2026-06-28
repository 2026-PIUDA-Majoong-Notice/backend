package com.notice.dto.log;

import com.notice.domain.log.MealAmount;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostMealLogRequest {

    @NotNull(message = "body[recordedAt] must not be null")
    private LocalDateTime recordedAt;

    @NotNull(message = "body[mealAmount] must not be null")
    private MealAmount mealAmount;
}