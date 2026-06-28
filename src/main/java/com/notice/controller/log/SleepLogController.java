package com.notice.controller.log;

import com.notice.dto.log.PostSleepLogRequest;
import com.notice.dto.log.PostSleepLogResponse;
import com.notice.service.log.PostSleepLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elders/{elderId}/logs/sleep")
@RequiredArgsConstructor
public class SleepLogController {

    private final PostSleepLogService postSleepLogService;

    @PostMapping
    public ResponseEntity<PostSleepLogResponse> postSleepLog(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @Valid @RequestBody PostSleepLogRequest request
    ) {
        PostSleepLogResponse response = postSleepLogService.execute(
                jwt,
                elderId,
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}