package com.notice.controller.log;

import com.notice.dto.log.PostHydrationLogRequest;
import com.notice.dto.log.PostHydrationLogResponse;
import com.notice.service.log.PostHydrationLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elders/{elderId}/logs/hydration")
@RequiredArgsConstructor
public class HydrationLogController {

    private final PostHydrationLogService postHydrationLogService;

    @PostMapping
    public ResponseEntity<PostHydrationLogResponse> postHydrationLog(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @Valid @RequestBody PostHydrationLogRequest request
    ) {
        PostHydrationLogResponse response = postHydrationLogService.execute(
                jwt,
                elderId,
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}