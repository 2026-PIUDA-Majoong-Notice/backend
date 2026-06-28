package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.domain.elder.ElderMedication;
import com.notice.dto.elder.PatchElderMedicationRequest;
import com.notice.dto.elder.PatchElderMedicationResponse;
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
public class PatchElderMedicationService {

    private final ElderRepository elderRepository;
    private final ElderMedicationRepository elderMedicationRepository;

    @Transactional
    public PatchElderMedicationResponse execute(
            Jwt jwt,
            Long elderId,
            Long medicationId,
            PatchElderMedicationRequest request
    ) {
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

        updateMedication(medication, request);

        return new PatchElderMedicationResponse(medication.getId());
    }

    private void updateMedication(
            ElderMedication medication,
            PatchElderMedicationRequest request
    ) {
        if (request.getMedicationType() != null) {
            medication.updateMedicationType(request.getMedicationType());
        }

        if (request.getTakenInMorning() != null) {
            medication.updateTakenInMorning(request.getTakenInMorning());
        }

        if (request.getTakenAtLunch() != null) {
            medication.updateTakenAtLunch(request.getTakenAtLunch());
        }

        if (request.getTakenInEvening() != null) {
            medication.updateTakenInEvening(request.getTakenInEvening());
        }

        if (request.getTakenBeforeSleep() != null) {
            medication.updateTakenBeforeSleep(request.getTakenBeforeSleep());
        }

        if (request.getActive() != null) {
            medication.updateActive(request.getActive());
        }
    }
}