package com.notice.service.user;

import com.notice.domain.user.User;
import com.notice.dto.user.PostUserRequest;
import com.notice.dto.user.PostUserResponse;
import com.notice.exception.HttpException;
import com.notice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PostUserResponse execute(PostUserRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        validatePasswordConfirmation(request);
        validateEmailNotDuplicated(normalizedEmail);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.create(normalizedEmail, encodedPassword);
        User savedUser = userRepository.save(user);

        return new PostUserResponse(savedUser.getId(), savedUser.getEmail());
    }

    private void validatePasswordConfirmation(PostUserRequest request) {
        boolean passwordMatched = request.getPassword().equals(request.getPasswordConfirm());

        if (passwordMatched == false) {
            throw new HttpException.BadRequest(
                    "Body[password] and Body[passwordConfirm] must be same"
            );
        }
    }

    private void validateEmailNotDuplicated(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new HttpException.Conflict(
                    "Body[email] must be unique"
            );
        }
    }
}