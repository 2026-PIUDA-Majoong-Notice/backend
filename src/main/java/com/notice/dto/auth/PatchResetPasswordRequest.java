package com.notice.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchResetPasswordRequest {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$")
    private String password;

    @NotBlank
    private String passwordConfirm;
}