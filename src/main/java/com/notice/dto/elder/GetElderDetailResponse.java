package com.notice.dto.elder;

import com.notice.domain.elder.BowelType;
import com.notice.domain.elder.CognitiveStatus;
import com.notice.domain.elder.Elder;
import com.notice.domain.elder.ElderMedication;
import com.notice.domain.elder.Gender;
import com.notice.domain.elder.MedicationType;
import com.notice.domain.elder.MobilityLevel;
import com.notice.domain.image.Image;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetElderDetailResponse {

    private final Long elderId;
    private final Long imageId;
    private final String imageUrl;

    private final String name;
    private final LocalDate birth;
    private final Gender gender;

    private final LocalTime wakeTime;
    private final LocalTime sleepTime;

    private final Boolean usesDiaper;
    private final BowelType bowelType;
    private final MobilityLevel mobilityLevel;
    private final CognitiveStatus cognitiveStatus;

    private final List<MedicationResponse> medications;

    public static GetElderDetailResponse from(Elder elder) {
        Image image = elder.getImage();

        Long imageId = image == null ? null : image.getId();
        String imageUrl = image == null ? null : image.getImageUrl();

        List<MedicationResponse> medications = elder.getMedications().stream()
                .map(MedicationResponse::from)
                .toList();

        return new GetElderDetailResponse(
                elder.getId(),
                imageId,
                imageUrl,
                elder.getName(),
                elder.getBirth(),
                elder.getGender(),
                elder.getWakeTime(),
                elder.getSleepTime(),
                elder.isUsesDiaper(),
                elder.getBowelType(),
                elder.getMobilityLevel(),
                elder.getCognitiveStatus(),
                medications
        );
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