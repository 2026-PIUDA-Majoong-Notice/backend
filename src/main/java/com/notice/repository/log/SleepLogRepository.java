package com.notice.repository.log;

import com.notice.domain.log.SleepLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {
}