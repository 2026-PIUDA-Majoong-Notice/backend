package com.notice.service.log;

import com.notice.domain.elder.Elder;
import com.notice.domain.log.MealLog;
import com.notice.dto.log.PostMealLogRequest;
import com.notice.dto.log.PostMealLogResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderRepository;
import com.notice.repository.log.MealLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMealLogService {

    private final ElderRepository elderRepository;
    private final MealLogRepository mealLogRepository;

    @Transactional
    public PostMealLogResponse execute(
            Jwt jwt,
            Long elderId,
            PostMealLogRequest request
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        MealLog mealLog = MealLog.create(
                elder,
                request.getRecordedAt(),
                request.getMealAmount()
        );

        MealLog savedMealLog = mealLogRepository.save(mealLog);

        return new PostMealLogResponse(savedMealLog.getId());
    }
}