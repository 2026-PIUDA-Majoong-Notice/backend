package com.notice.service.log;

import com.notice.domain.elder.Elder;
import com.notice.domain.log.HydrationLog;
import com.notice.dto.log.PostHydrationLogRequest;
import com.notice.dto.log.PostHydrationLogResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderRepository;
import com.notice.repository.log.HydrationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHydrationLogService {

    private final ElderRepository elderRepository;
    private final HydrationLogRepository hydrationLogRepository;

    @Transactional
    public PostHydrationLogResponse execute(
            Jwt jwt,
            Long elderId,
            PostHydrationLogRequest request
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        HydrationLog hydrationLog = HydrationLog.create(
                elder,
                request.getRecordedAt(),
                request.getHydrationAmount()
        );

        HydrationLog savedHydrationLog = hydrationLogRepository.save(hydrationLog);

        return new PostHydrationLogResponse(savedHydrationLog.getId());
    }
}