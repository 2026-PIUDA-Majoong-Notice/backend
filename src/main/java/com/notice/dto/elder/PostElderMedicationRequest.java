package com.notice.dto.elder;

import com.notice.domain.elder.MedicationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostElderMedicationRequest {

    @Valid
    @NotEmpty(message = "body[medications] must not be empty")
    private List<MedicationRequest> medications;

    @Getter
    @NoArgsConstructor
    public static class MedicationRequest {

        @NotNull(message = "body[medications].medicationType must not be null")
        private MedicationType medicationType;

        @NotNull(message = "body[medications].takenInMorning must not be null")
        private Boolean takenInMorning;

        @NotNull(message = "body[medications].takenAtLunch must not be null")
        private Boolean takenAtLunch;

        @NotNull(message = "body[medications].takenInEvening must not be null")
        private Boolean takenInEvening;

        @NotNull(message = "body[medications].takenBeforeSleep must not be null")
        private Boolean takenBeforeSleep;
    }
}