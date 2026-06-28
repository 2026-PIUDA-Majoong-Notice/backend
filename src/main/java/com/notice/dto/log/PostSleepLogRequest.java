package com.notice.dto.log;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostSleepLogRequest {

    private LocalDateTime wakeTime;

    private LocalDateTime sleepTime;
}