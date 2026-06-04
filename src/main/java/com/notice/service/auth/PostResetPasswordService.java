package com.notice.service.auth;

import com.notice.domain.auth.PasswordResetToken;
import com.notice.domain.user.User;
import com.notice.dto.auth.PostResetPasswordRequest;
import com.notice.dto.auth.PostResetPasswordResponse;
import com.notice.exception.HttpException;
import com.notice.repository.auth.PasswordResetTokenRepository;
import com.notice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender javaMailSender;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Transactional
    public PostResetPasswordResponse execute(PostResetPasswordRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new HttpException.NotFound(
                        "body[email] must be registered"
                ));

        String token = createToken();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken passwordResetToken = PasswordResetToken.create(
                token,
                user,
                expiresAt
        );

        passwordResetTokenRepository.save(passwordResetToken);

        sendResetPasswordMail(user.getEmail(), token);

        return new PostResetPasswordResponse("비밀번호 재설정 메일을 발송했습니다.");
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }

    private void sendResetPasswordMail(String email, String token) {
        String resetUrl = frontendUrl + "/resetPassword?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Notice] 비밀번호 재설정 안내");
        message.setText(
                "비밀번호 재설정을 요청하셨습니다.\n\n"
                        + "아래 링크를 통해 비밀번호를 재설정해주세요.\n"
                        + resetUrl
                        + "\n\n"
        );

        javaMailSender.send(message);
    }
}