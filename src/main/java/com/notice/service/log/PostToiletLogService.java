package com.notice.service.log;

import com.notice.domain.elder.Elder;
import com.notice.domain.log.ToiletLog;
import com.notice.dto.log.PostToiletLogRequest;
import com.notice.dto.log.PostToiletLogResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderRepository;
import com.notice.repository.log.ToiletLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostToiletLogService {

    private final ElderRepository elderRepository;
    private final ToiletLogRepository toiletLogRepository;

    @Transactional
    public PostToiletLogResponse execute(
            Jwt jwt,
            Long elderId,
            PostToiletLogRequest request
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        ToiletLog toiletLog = ToiletLog.create(
                elder,
                request.getToiletType(),
                request.getRecordedAt()
        );

        ToiletLog savedToiletLog = toiletLogRepository.save(toiletLog);

        return new PostToiletLogResponse(savedToiletLog.getId());
    }
}