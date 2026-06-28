package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.dto.elder.GetElderDetailResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetElderDetailService {

    private final ElderRepository elderRepository;

    public GetElderDetailResponse execute(Jwt jwt, Long elderId) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        return GetElderDetailResponse.from(elder);
    }
}