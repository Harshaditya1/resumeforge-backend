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
public class AnalysisResponseDto {

    // Existing extracted keywords
    private List<String> resumeKeywords;

    private List<String> jobDescriptionKeywords;

    // ATS Matching Results
    private List<String> matchedKeywords;

    private List<String> missingKeywords;

    private double matchPercentage;
}