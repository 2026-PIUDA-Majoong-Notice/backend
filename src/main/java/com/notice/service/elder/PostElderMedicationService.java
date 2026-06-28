package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.domain.elder.ElderMedication;
import com.notice.dto.elder.PostElderMedicationRequest;
import com.notice.dto.elder.PostElderMedicationResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderMedicationRepository;
import com.notice.repository.elder.ElderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostElderMedicationService {

    private final ElderRepository elderRepository;
    private final ElderMedicationRepository elderMedicationRepository;

    @Transactional
    public PostElderMedicationResponse execute(
            Jwt jwt,
            Long elderId,
            PostElderMedicationRequest request
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        List<ElderMedication> medications = request.getMedications().stream()
                .map(medicationRequest -> {
                    ElderMedication medication = ElderMedication.create(
                            medicationRequest.getMedicationType(),
                            medicationRequest.getTakenInMorning(),
                            medicationRequest.getTakenAtLunch(),
                            medicationRequest.getTakenInEvening(),
                            medicationRequest.getTakenBeforeSleep()
                    );

                    elder.addMedication(medication);

                    return medication;
                })
                .toList();

        List<ElderMedication> savedMedications = elderMedicationRepository.saveAll(medications);

        List<Long> medicationIds = savedMedications.stream()
                .map(ElderMedication::getId)
                .toList();

        return new PostElderMedicationResponse(medicationIds);
    }
}