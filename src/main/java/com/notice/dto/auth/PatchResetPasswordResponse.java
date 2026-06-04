package com.notice.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PatchResetPasswordResponse {

    private final String message;
}