package com.resumeforge.analysis.mapper;

import com.resumeforge.analysis.Analysis;
import com.resumeforge.analysis.dto.*;

public class AnalysisMapper {

    private AnalysisMapper() {
    }

    /**
     * Convert Analysis Entity -> AnalysisResponseDto
     */
    public static AnalysisResponseDto toResponseDto(Analysis analysis) {

        if (analysis == null) {
            return null;
        }

        return AnalysisResponseDto.builder()
                .resumeKeywords(analysis.getResumeKeywords())
                .jobDescriptionKeywords(analysis.getJobDescriptionKeywords())
                .matchedKeywords(analysis.getMatchedKeywords())
                .missingKeywords(analysis.getMissingKeywords())
                .matchPercentage(analysis.getMatchPercentage())
                .report(
                        AtsReportDto.builder()
                                .overallAssessment(analysis.getOverallAssessment())
                                .scoreCategory(analysis.getScoreCategory())
                                .strengths(analysis.getStrengths())
                                .improvements(analysis.getImprovements())
                                .recommendation(analysis.getRecommendation())
                                .build()
                )
                .improvement(
                        ResumeImprovementDto.builder()
                                .missingSkills(analysis.getAiMissingSkills())
                                .projectSuggestions(analysis.getAiProjectSuggestions())
                                .summarySuggestions(analysis.getAiSummarySuggestions())
                                .atsSuggestions(analysis.getAiAtsSuggestions())
                                .build()
                )
                .aiAnalysis(
                        AiAnalysisResponseDto.builder()
                                .overallScore(analysis.getAiOverallScore())
                                .overallAssessment(analysis.getAiOverallAssessment())
                                .missingSkills(analysis.getAiMissingSkills())
                                .projectSuggestions(analysis.getAiProjectSuggestions())
                                .summarySuggestions(analysis.getAiSummarySuggestions())
                                .atsSuggestions(analysis.getAiAtsSuggestions())
                                .build()
                )
                .build();
    }
}