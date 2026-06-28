package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.dto.elder.GetElderResponse;
import com.notice.repository.elder.ElderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetElderService {

    private final ElderRepository elderRepository;

    public GetElderResponse execute(Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());

        List<Elder> elders = elderRepository.findAllByUserId(userId);

        return GetElderResponse.from(elders);
    }
}