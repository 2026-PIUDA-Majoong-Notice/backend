package com.notice.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostLoginResponse {

    private final String accessToken;
    private final String refreshToken;
}
