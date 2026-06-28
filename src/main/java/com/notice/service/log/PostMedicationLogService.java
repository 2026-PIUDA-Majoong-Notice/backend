package com.notice.service.log;

import com.notice.domain.elder.Elder;
import com.notice.domain.elder.ElderMedication;
import com.notice.domain.log.MedicationLog;
import com.notice.domain.log.MedicationTimeSlot;
import com.notice.dto.log.PostMedicationLogRequest;
import com.notice.dto.log.PostMedicationLogResponse;
import com.notice.exception.HttpException;
import com.notice.repository.elder.ElderMedicationRepository;
import com.notice.repository.elder.ElderRepository;
import com.notice.repository.log.MedicationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMedicationLogService {

    private final ElderRepository elderRepository;
    private final ElderMedicationRepository elderMedicationRepository;
    private final MedicationLogRepository medicationLogRepository;

    @Transactional
    public PostMedicationLogResponse execute(
            Jwt jwt,
            Long elderId,
            PostMedicationLogRequest request
    ) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        validateDuplicateMedicationIds(request);

        List<MedicationLog> medicationLogs = request.getMedications().stream()
                .map(medicationRequest -> {
                    ElderMedication medication = elderMedicationRepository.findByIdAndElderId(
                                    medicationRequest.getMedicationId(),
                                    elder.getId()
                            )
                            .orElseThrow(() -> new HttpException.NotFound(
                                    "body[medications].medicationId must be valid"
                            ));

                    validateMedicationIsActive(medication);
                    validateTimeSlot(medication, request.getTimeSlot());

                    return MedicationLog.create(
                            elder,
                            medication,
                            request.getLogDate(),
                            request.getTimeSlot(),
                            medicationRequest.getTaken()
                    );
                })
                .toList();

        List<MedicationLog> savedMedicationLogs = medicationLogRepository.saveAll(medicationLogs);

        List<Long> medicationLogIds = savedMedicationLogs.stream()
                .map(MedicationLog::getId)
                .toList();

        return new PostMedicationLogResponse(medicationLogIds);
    }

    private void validateDuplicateMedicationIds(PostMedicationLogRequest request) {
        Set<Long> medicationIds = new HashSet<>();

        request.getMedications().forEach(medication -> {
            if (medicationIds.add(medication.getMedicationId()) == false) {
                throw new HttpException.BadRequest(
                        "body[medications].medicationId must not be duplicated"
                );
            }
        });
    }

    private void validateMedicationIsActive(ElderMedication medication) {
        if (medication.isActive() == false) {
            throw new HttpException.BadRequest(
                    "body[medications].medicationId must be active"
            );
        }
    }

    private void validateTimeSlot(
            ElderMedication medication,
            MedicationTimeSlot timeSlot
    ) {
        boolean scheduled = switch (timeSlot) {
            case MORNING -> medication.isTakenInMorning();
            case LUNCH -> medication.isTakenAtLunch();
            case EVENING -> medication.isTakenInEvening();
            case BEFORE_SLEEP -> medication.isTakenBeforeSleep();
        };

        if (scheduled == false) {
            throw new HttpException.BadRequest(
                    "body[medications].medicationId must be scheduled for this timeSlot"
            );
        }
    }
}