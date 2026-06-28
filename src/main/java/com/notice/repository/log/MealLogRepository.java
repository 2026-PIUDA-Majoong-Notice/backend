package com.notice.repository.log;

import com.notice.domain.log.MealLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealLogRepository extends JpaRepository<MealLog, Long> {
}