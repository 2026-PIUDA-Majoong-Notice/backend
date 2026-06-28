package com.notice.repository.log;

import com.notice.domain.log.HydrationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HydrationLogRepository extends JpaRepository<HydrationLog, Long> {
}