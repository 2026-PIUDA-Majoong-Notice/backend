package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.domain.elder.ElderMedication;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderMedicationRepository;
import com.notice.repository.elder.ElderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeleteElderMedicationService {

    private final ElderRepository elderRepository;
    private final ElderMedicationRepository elderMedicationRepository;

    @Transactional
    public void execute(Jwt jwt, Long elderId, Long medicationId) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        ElderMedication medication = elderMedicationRepository.findByIdAndElderId(
                        medicationId,
                        elder.getId()
                )
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[medicationId] must be valid"
                ));

        elder.removeMedication(medication);
    }
}