package com.notice.repository.elder;

import com.notice.domain.elder.Elder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElderRepository extends JpaRepository<Elder, Long> {

    @EntityGraph(attributePaths = {"image"})
    List<Elder> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"image", "medications"})
    Optional<Elder> findByIdAndUserId(Long elderId, Long userId);
}