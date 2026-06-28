package com.notice.dto.elder;

import com.notice.domain.elder.BowelType;
import com.notice.domain.elder.CognitiveStatus;
import com.notice.domain.elder.Gender;
import com.notice.domain.elder.MobilityLevel;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class PatchElderRequest {

    private Long imageId;

    @Size(max = 30, message = "body[name] must be less than or equal to 30 characters")
    private String name;

    private LocalDate birth;

    private Gender gender;

    private LocalTime wakeTime;

    private LocalTime sleepTime;

    private Boolean usesDiaper;

    private BowelType bowelType;

    private MobilityLevel mobilityLevel;

    private CognitiveStatus cognitiveStatus;
}