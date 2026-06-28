package com.notice.dto.elder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostElderMedicationResponse {

    private final List<Long> medicationIds;
}