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
public class ResumeImprovementDto {

    // Skills that should be added or strengthened
    private List<String> missingSkills;

    // Suggestions for improving project descriptions
    private List<String> projectSuggestions;

    // Suggestions for improving the professional summary
    private List<String> summarySuggestions;

    // General ATS improvement tips
    private List<String> atsSuggestions;
}