package com.notice.dto.log;

import com.notice.domain.log.MedicationTimeSlot;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostMedicationLogRequest {

    @NotNull(message = "body[logDate] must not be null")
    private LocalDate logDate;

    @NotNull(message = "body[timeSlot] must not be null")
    private MedicationTimeSlot timeSlot;

    @Valid
    @NotEmpty(message = "body[medications] must not be empty")
    private List<MedicationRequest> medications;

    @Getter
    @NoArgsConstructor
    public static class MedicationRequest {

        @NotNull(message = "body[medications].medicationId must not be null")
        private Long medicationId;

        @NotNull(message = "body[medications].taken must not be null")
        private Boolean taken;
    }
}