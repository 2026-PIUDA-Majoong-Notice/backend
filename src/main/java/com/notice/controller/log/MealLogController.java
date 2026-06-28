package com.notice.controller.log;

import com.notice.dto.log.PostMealLogRequest;
import com.notice.dto.log.PostMealLogResponse;
import com.notice.service.log.PostMealLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elders/{elderId}/logs/meal")
@RequiredArgsConstructor
public class MealLogController {

    private final PostMealLogService postMealLogService;

    @PostMapping
    public ResponseEntity<PostMealLogResponse> postMealLog(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @Valid @RequestBody PostMealLogRequest request
    ) {
        PostMealLogResponse response = postMealLogService.execute(
                jwt,
                elderId,
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}