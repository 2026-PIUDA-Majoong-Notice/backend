package com.notice.dto.elder;

import com.notice.domain.elder.BowelType;
import com.notice.domain.elder.CognitiveStatus;
import com.notice.domain.elder.Gender;
import com.notice.domain.elder.MobilityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class PostElderRequest {

    private Long imageId;

    @NotBlank(message = "body[name] must not be blank")
    @Size(max = 30, message = "body[name] must be less than or equal to 30 characters")
    private String name;

    @NotNull(message = "body[birth] must not be null")
    private LocalDate birth;

    @NotNull(message = "body[gender] must not be null")
    private Gender gender;

    @NotNull(message = "body[wakeTime] must not be null")
    private LocalTime wakeTime;

    @NotNull(message = "body[sleepTime] must not be null")
    private LocalTime sleepTime;

    @NotNull(message = "body[usesDiaper] must not be null")
    private Boolean usesDiaper;

    @NotNull(message = "body[bowelType] must not be null")
    private BowelType bowelType;

    @NotNull(message = "body[mobilityLevel] must not be null")
    private MobilityLevel mobilityLevel;

    @NotNull(message = "body[cognitiveStatus] must not be null")
    private CognitiveStatus cognitiveStatus;
}