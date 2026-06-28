package com.notice.repository.log;

import com.notice.domain.log.ToiletLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiletLogRepository extends JpaRepository<ToiletLog, Long> {
}