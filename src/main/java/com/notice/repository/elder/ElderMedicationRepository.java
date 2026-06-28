package com.notice.repository.elder;

import com.notice.domain.elder.ElderMedication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ElderMedicationRepository extends JpaRepository<ElderMedication, Long> {

    Optional<ElderMedication> findByIdAndElderId(Long medicationId, Long elderId);

    List<ElderMedication> findAllByElderId(Long elderId);
}