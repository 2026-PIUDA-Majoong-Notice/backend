package com.notice.service.elder;

import com.notice.domain.elder.Elder;
import com.notice.domain.elder.ElderMedication;
import com.notice.dto.elder.GetElderMedicationResponse;
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
public class GetElderMedicationService {

    private final ElderRepository elderRepository;
    private final ElderMedicationRepository elderMedicationRepository;

    public GetElderMedicationResponse execute(Jwt jwt, Long elderId) {
        Long userId = Long.valueOf(jwt.getSubject());

        Elder elder = elderRepository.findByIdAndUserId(elderId, userId)
                .orElseThrow(() -> new HttpException.NotFound(
                        "path[elderId] must be valid"
                ));

        List<ElderMedication> medications = elderMedicationRepository.findAllByElderId(
                elder.getId()
        );

        return GetElderMedicationResponse.from(medications);
    }
}