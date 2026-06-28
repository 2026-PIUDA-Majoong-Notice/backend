package com.notice.dto.log;

import com.notice.domain.log.ToiletType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostToiletLogRequest {

    @NotNull(message = "body[toiletType] must not be null")
    private ToiletType toiletType;

    @NotNull(message = "body[recordedAt] must not be null")
    private LocalDateTime recordedAt;
}