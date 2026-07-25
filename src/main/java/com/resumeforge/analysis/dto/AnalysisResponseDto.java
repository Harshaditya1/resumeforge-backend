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

    // Extracted Resume Keywords
    private List<String> resumeKeywords;

    // Extracted Job Description Keywords
    private List<String> jobDescriptionKeywords;

    // Common Keywords
    private List<String> matchedKeywords;

    // Missing Keywords
    private List<String> missingKeywords;

    // ATS Match Score
    private double matchPercentage;

    // Professional ATS Report
    private AtsReportDto report;

    // Rule-based Resume Improvement Suggestions
    private ResumeImprovementDto improvement;

    // AI-powered Resume Analysis
    private AiAnalysisResponseDto aiAnalysis;
}