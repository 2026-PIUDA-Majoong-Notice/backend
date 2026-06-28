package com.notice.controller.log;

import com.notice.dto.log.PostMedicationLogRequest;
import com.notice.dto.log.PostMedicationLogResponse;
import com.notice.service.log.PostMedicationLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elders/{elderId}/logs/medication")
@RequiredArgsConstructor
public class MedicationLogController {

    private final PostMedicationLogService postMedicationLogService;

    @PostMapping
    public ResponseEntity<PostMedicationLogResponse> postMedicationLog(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @Valid @RequestBody PostMedicationLogRequest request
    ) {
        PostMedicationLogResponse response = postMedicationLogService.execute(
                jwt,
                elderId,
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}