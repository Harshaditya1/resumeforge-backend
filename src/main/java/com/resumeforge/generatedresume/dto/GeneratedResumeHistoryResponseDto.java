package com.resumeforge.generatedresume.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GeneratedResumeHistoryResponseDto {

    private Long id;

    private Long resumeId;

    private Long jobDescriptionId;

    private LocalDateTime createdAt;
}