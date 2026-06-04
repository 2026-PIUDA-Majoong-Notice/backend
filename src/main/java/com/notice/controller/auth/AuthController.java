package com.notice.controller.auth;

import com.notice.dto.auth.*;
import com.notice.service.auth.PatchResetPasswordService;
import com.notice.service.auth.PostLoginService;
import com.notice.service.auth.PostResetPasswordService;
import com.notice.service.auth.PostTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PostLoginService postLoginService;
    private final PostTokenService postTokenService;
    private final PostResetPasswordService postResetPasswordService;
    private final PatchResetPasswordService patchResetPasswordService;

    @PostMapping("/login")
    public ResponseEntity<PostLoginResponse> postLogin(
            @Valid @RequestBody PostLoginRequest request
    ) {
        PostLoginResponse response = postLoginService.execute(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/token")
    public ResponseEntity<PostTokenResponse> postToken(
            @Valid @RequestBody PostTokenRequest request
    ) {
        PostTokenResponse response = postTokenService.execute(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<PostResetPasswordResponse> postResetPassword(
            @Valid @RequestBody PostResetPasswordRequest request
    ) {
        PostResetPasswordResponse response = postResetPasswordService.execute(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/resetPassword")
    public ResponseEntity<PatchResetPasswordResponse> patchResetPassword(
            @Valid @RequestBody PatchResetPasswordRequest request
    ) {
        PatchResetPasswordResponse response = patchResetPasswordService.execute(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}