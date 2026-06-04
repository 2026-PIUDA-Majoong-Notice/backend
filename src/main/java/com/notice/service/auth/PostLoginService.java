package com.notice.service.auth;

import com.notice.domain.user.User;
import com.notice.dto.auth.PostLoginRequest;
import com.notice.dto.auth.PostLoginResponse;
import com.notice.exception.HttpException;
import com.notice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.access-token-expiration-seconds}")
    private long accessTokenExpirationSeconds;

    @Value("${jwt.refresh-token-expiration-seconds}")
    private long refreshTokenExpirationSeconds;

    public PostLoginResponse execute(PostLoginRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new HttpException.Unauthorized(
                        "Body[email] and Body[password] must be valid"
                ));

        validatePassword(request.getPassword(), user.getPassword());

        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);

        return new PostLoginResponse(accessToken, refreshToken);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        boolean passwordMatched = passwordEncoder.matches(rawPassword, encodedPassword);

        if (passwordMatched == false) {
            throw new HttpException.Unauthorized(
                    "Body[email] and Body[password] must be valid"
            );
        }
    }

    private String createAccessToken(User user) {
        Instant now = Instant.now();

        JwsHeader headers = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessTokenExpirationSeconds))
                .subject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .claim("tokenType", "access")
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(headers, claims)
        ).getTokenValue();
    }

    private String createRefreshToken(User user) {
        Instant now = Instant.now();

        JwsHeader headers = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(refreshTokenExpirationSeconds))
                .subject(String.valueOf(user.getId()))
                .claim("tokenType", "refresh")
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(headers, claims)
        ).getTokenValue();
    }
}