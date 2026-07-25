package com.resumeforge.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiAnalysisResponseDto {

    private Integer overallScore;

    private String overallAssessment;

    private List<String> missingSkills;

    private List<String> summarySuggestions;

    private List<String> projectSuggestions;

    private List<String> atsSuggestions;
}