package com.resumeforge.analysis.dto;

import com.resumeforge.analysis.model.AtsSkillProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponseDto {

    /*
     * ============================
     * Legacy ATS V1 Fields
     * (Kept for backward compatibility)
     * ============================
     */

    private List<String> resumeKeywords;

    private List<String> jobDescriptionKeywords;

    private List<String> matchedKeywords;

    private List<String> missingKeywords;

    private double matchPercentage;

    /*
     * ============================
     * ATS V2 Fields
     * ============================
     */

    private AtsSkillProfile resumeSkillProfile;

    private AtsSkillProfile jobDescriptionSkillProfile;

    private Map<String, List<String>> matchedSkillsByCategory;

    private Map<String, List<String>> missingSkillsByCategory;

    /*
     * ============================
     * Existing Response Objects
     * ============================
     */

    private AtsReportDto report;

    private ResumeImprovementDto improvement;

    private AiAnalysisResponseDto aiAnalysis;
}