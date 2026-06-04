package com.notice.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLoginRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
