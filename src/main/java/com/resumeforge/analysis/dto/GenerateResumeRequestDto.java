package com.resumeforge.analysis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateResumeRequestDto {

    @NotNull(message = "Resume ID is required")
    private Long resumeId;

    @NotNull(message = "Job Description ID is required")
    private Long jobDescriptionId;
}