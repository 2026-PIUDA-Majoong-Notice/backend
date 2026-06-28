package com.notice.dto.elder;

import com.notice.domain.elder.ElderMedication;
import com.notice.domain.elder.MedicationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetElderMedicationResponse {

    private final List<MedicationResponse> medications;

    public static GetElderMedicationResponse from(List<ElderMedication> medications) {
        List<MedicationResponse> responses = medications.stream()
                .map(MedicationResponse::from)
                .toList();

        return new GetElderMedicationResponse(responses);
    }

    @Getter
    @RequiredArgsConstructor
    public static class MedicationResponse {

        private final Long medicationId;
        private final MedicationType medicationType;
        private final Boolean takenInMorning;
        private final Boolean takenAtLunch;
        private final Boolean takenInEvening;
        private final Boolean takenBeforeSleep;
        private final Boolean active;

        public static MedicationResponse from(ElderMedication medication) {
            return new MedicationResponse(
                    medication.getId(),
                    medication.getMedicationType(),
                    medication.isTakenInMorning(),
                    medication.isTakenAtLunch(),
                    medication.isTakenInEvening(),
                    medication.isTakenBeforeSleep(),
                    medication.isActive()
            );
        }
    }
}