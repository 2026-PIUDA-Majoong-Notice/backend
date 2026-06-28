package com.notice.dto.log;

import com.notice.domain.log.HydrationAmount;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostHydrationLogRequest {

    @NotNull(message = "body[recordedAt] must not be null")
    private LocalDateTime recordedAt;

    @NotNull(message = "body[hydrationAmount] must not be null")
    private HydrationAmount hydrationAmount;
}