package com.notice.service.log;

import com.notice.domain.elder.Elder;
import com.notice.domain.log.SleepLog;
import com.notice.dto.log.PostSleepLogRequest;
import com.notice.dto.log.PostSleepLogResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderRepository;
import com.notice.repository.log.SleepLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSleepLogService {

    private final ElderRepository elderRepository;
    private final SleepLogRepository sleepLogRepository;

    @Transactional
    public PostSleepLogResponse execute(
            Jwt jwt,
            Long elderId,
            PostSleepLogRequest request
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        validateSleepLog(request);

        SleepLog sleepLog = SleepLog.create(
                elder,
                request.getWakeTime(),
                request.getSleepTime()
        );

        SleepLog savedSleepLog = sleepLogRepository.save(sleepLog);

        return new PostSleepLogResponse(savedSleepLog.getId());
    }

    private void validateSleepLog(PostSleepLogRequest request) {
        if (request.getWakeTime() == null && request.getSleepTime() == null) {
            throw new HttpException.BadRequest(
                    "body[wakeTime] or body[sleepTime] must not be null"
            );
        }

        if (
                request.getWakeTime() != null &&
                        request.getSleepTime() != null &&
                        request.getWakeTime().isBefore(request.getSleepTime())
        ) {
            throw new HttpException.BadRequest(
                    "body[wakeTime] must be after body[sleepTime]"
            );
        }
    }
}