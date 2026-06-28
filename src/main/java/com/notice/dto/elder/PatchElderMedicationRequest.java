package com.notice.dto.elder;

import com.notice.domain.elder.MedicationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchElderMedicationRequest {

    private MedicationType medicationType;

    private Boolean takenInMorning;

    private Boolean takenAtLunch;

    private Boolean takenInEvening;

    private Boolean takenBeforeSleep;

    private Boolean active;
}