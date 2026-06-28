package com.notice.dto.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostMedicationLogResponse {

    private final List<Long> medicationLogIds;
}