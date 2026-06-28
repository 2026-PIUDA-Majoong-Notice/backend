package com.notice.controller.elder;

import com.notice.dto.elder.*;
import com.notice.service.elder.DeleteElderMedicationService;
import com.notice.service.elder.GetElderMedicationService;
import com.notice.service.elder.PatchElderMedicationService;
import com.notice.service.elder.PostElderMedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elders/{elderId}/medications")
@RequiredArgsConstructor
public class ElderMedicationController {

    private final PostElderMedicationService postElderMedicationService;
    private final GetElderMedicationService getElderMedicationService;
    private final PatchElderMedicationService patchElderMedicationService;
    private final DeleteElderMedicationService deleteElderMedicationService;

    @PostMapping
    public ResponseEntity<PostElderMedicationResponse> postElderMedication(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @Valid @RequestBody PostElderMedicationRequest request
    ) {
        PostElderMedicationResponse response = postElderMedicationService.execute(
                jwt,
                elderId,
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetElderMedicationResponse> getElderMedication(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId
    ) {
        GetElderMedicationResponse response = getElderMedicationService.execute(
                jwt,
                elderId
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{medicationId}")
    public ResponseEntity<PatchElderMedicationResponse> patchElderMedication(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @PathVariable Long medicationId,
            @RequestBody PatchElderMedicationRequest request
    ) {
        PatchElderMedicationResponse response = patchElderMedicationService.execute(
                jwt,
                elderId,
                medicationId,
                request
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{medicationId}")
    public ResponseEntity<Void> deleteElderMedication(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @PathVariable Long medicationId
    ) {
        deleteElderMedicationService.execute(jwt, elderId, medicationId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}