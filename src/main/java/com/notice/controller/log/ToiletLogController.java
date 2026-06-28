package com.notice.controller.log;

import com.notice.dto.log.PostToiletLogRequest;
import com.notice.dto.log.PostToiletLogResponse;
import com.notice.service.log.PostToiletLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elders/{elderId}/logs/toilet")
@RequiredArgsConstructor
public class ToiletLogController {

    private final PostToiletLogService postToiletLogService;

    @PostMapping
    public ResponseEntity<PostToiletLogResponse> postToiletLog(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @Valid @RequestBody PostToiletLogRequest request
    ) {
        PostToiletLogResponse response = postToiletLogService.execute(
                jwt,
                elderId,
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}