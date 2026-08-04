package com.resumeforge.analysis.dto;

import java.util.List;

public class AIResumeTailoringResponseDto {

    private String professionalSummary;

    private List<String> skills;

    private List<String> experienceSuggestions;

    private List<String> projectSuggestions;

    private List<String> missingKeywords;

    private List<String> overallSuggestions;

    public AIResumeTailoringResponseDto() {
    }

    public String getProfessionalSummary() {
        return professionalSummary;
    }

    public void setProfessionalSummary(String professionalSummary) {
        this.professionalSummary = professionalSummary;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getExperienceSuggestions() {
        return experienceSuggestions;
    }

    public void setExperienceSuggestions(List<String> experienceSuggestions) {
        this.experienceSuggestions = experienceSuggestions;
    }

    public List<String> getProjectSuggestions() {
        return projectSuggestions;
    }

    public void setProjectSuggestions(List<String> projectSuggestions) {
        this.projectSuggestions = projectSuggestions;
    }

    public List<String> getMissingKeywords() {
        return missingKeywords;
    }

    public void setMissingKeywords(List<String> missingKeywords) {
        this.missingKeywords = missingKeywords;
    }

    public List<String> getOverallSuggestions() {
        return overallSuggestions;
    }

    public void setOverallSuggestions(List<String> overallSuggestions) {
        this.overallSuggestions = overallSuggestions;
    }
}