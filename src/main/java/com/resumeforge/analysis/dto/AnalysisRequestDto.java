package com.resumeforge.analysis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnalysisRequestDto {

    @NotNull
    private Long resumeId;

    @NotNull
    private Long jobDescriptionId;
}