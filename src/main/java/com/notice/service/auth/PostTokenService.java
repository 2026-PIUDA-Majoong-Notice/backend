package com.notice.service.auth;

import com.notice.domain.user.User;
import com.notice.dto.auth.PostTokenRequest;
import com.notice.dto.auth.PostTokenResponse;
import com.notice.exception.HttpException;
import com.notice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostTokenService {

    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.access-token-expiration-seconds}")
    private long accessTokenExpirationSeconds;

    public PostTokenResponse execute(PostTokenRequest request) {
        Jwt refreshToken = decodeRefreshToken(request.getRefreshToken());

        validateRefreshToken(refreshToken);

        Long userId = Long.valueOf(refreshToken.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HttpException.Unauthorized(
                        "body[refreshToken] must be valid"
                ));

        String accessToken = createAccessToken(user);

        return new PostTokenResponse(accessToken);
    }

    private Jwt decodeRefreshToken(String refreshToken) {
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (JwtException exception) {
            throw new HttpException.Unauthorized(
                    "body[refreshToken] must be valid"
            );
        }
    }

    private void validateRefreshToken(Jwt refreshToken) {
        String tokenType = refreshToken.getClaimAsString("tokenType");

        boolean refreshTokenTypeMatched = "refresh".equals(tokenType);

        if (refreshTokenTypeMatched == false) {
            throw new HttpException.Unauthorized(
                    "body[refreshToken] must be valid"
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
}