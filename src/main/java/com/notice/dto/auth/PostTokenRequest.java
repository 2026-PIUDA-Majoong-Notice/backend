package com.notice.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostTokenRequest {

    @NotBlank
    private String refreshToken;
}