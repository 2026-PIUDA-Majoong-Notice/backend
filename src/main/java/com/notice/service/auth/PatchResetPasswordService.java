package com.notice.service.auth;

import com.notice.domain.auth.PasswordResetToken;
import com.notice.domain.user.User;
import com.notice.dto.auth.PatchResetPasswordRequest;
import com.notice.dto.auth.PatchResetPasswordResponse;
import com.notice.exception.HttpException;
import com.notice.repository.auth.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatchResetPasswordService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PatchResetPasswordResponse execute(PatchResetPasswordRequest request) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new HttpException.Unauthorized(
                        "body[token] must be valid"
                ));

        validateToken(passwordResetToken);
        validatePasswordConfirmation(request);

        User user = passwordResetToken.getUser();

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        user.updatePassword(encodedPassword);
        passwordResetToken.use();

        return new PatchResetPasswordResponse("비밀번호가 재설정되었습니다.");
    }

    private void validateToken(PasswordResetToken passwordResetToken) {
        if (passwordResetToken.isUsed()) {
            throw new HttpException.Unauthorized(
                    "body[token] must be valid"
            );
        }

        if (passwordResetToken.isExpired()) {
            throw new HttpException.Unauthorized(
                    "body[token] must be valid"
            );
        }
    }

    private void validatePasswordConfirmation(PatchResetPasswordRequest request) {
        boolean passwordMatched = request.getPassword().equals(request.getPasswordConfirm());

        if (passwordMatched == false) {
            throw new HttpException.BadRequest(
                    "body[password] and body[passwordConfirm] must be same"
            );
        }
    }
}