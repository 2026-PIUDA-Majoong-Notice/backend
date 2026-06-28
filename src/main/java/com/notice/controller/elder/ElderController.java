package com.notice.controller.elder;

import com.notice.dto.elder.*;
import com.notice.service.elder.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elders")
@RequiredArgsConstructor
public class ElderController {

    private final PostElderService postElderService;
    private final GetElderService getElderService;
    private final GetElderDetailService getElderDetailService;
    private final PatchElderService patchElderService;
    private final DeleteElderService deleteElderService;

    @PostMapping
    public ResponseEntity<PostElderResponse> postElder(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody PostElderRequest request
    ) {
        PostElderResponse response = postElderService.execute(jwt, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetElderResponse> getElders(
            @AuthenticationPrincipal Jwt jwt
    ) {
        GetElderResponse response = getElderService.execute(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{elderId}")
    public ResponseEntity<GetElderDetailResponse> getElderDetail(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId
    ) {
        GetElderDetailResponse response = getElderDetailService.execute(jwt, elderId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{elderId}")
    public ResponseEntity<PatchElderResponse> patchElder(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId,
            @Valid @RequestBody PatchElderRequest request
    ) {
        PatchElderResponse response = patchElderService.execute(jwt, elderId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{elderId}")
    public ResponseEntity<Void> deleteElder(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long elderId
    ) {
        deleteElderService.execute(jwt, elderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}