package com.resumeforge.analysis.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AnalysisResponseDto {

    private Set<String> resumeKeywords;

    private Set<String> jobKeywords;
}