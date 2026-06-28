package com.notice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUserRequest {

    @NotBlank(message = "body[email] must not be blank")
    @Email(message = "body[email] must be valid")
    private String email;

    @NotBlank(message = "body[password] must not be blank")
    @Size(min = 8, max = 64, message = "body[password] must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "body[password] must contain letters and numbers"
    )
    private String password;

    @NotBlank(message = "body[passwordConfirm] must not be blank")
    private String passwordConfirm;

    @NotBlank(message = "body[name] must not be blank")
    @Size(max = 30, message = "body[name] must be less than or equal to 30 characters")
    private String name;
}