package com.resumeforge.generatedresume.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GeneratedResumeDetailsResponseDto {

    private Long id;

    private Long resumeId;

    private Long jobDescriptionId;

    private String generatedResume;

    private LocalDateTime createdAt;
}