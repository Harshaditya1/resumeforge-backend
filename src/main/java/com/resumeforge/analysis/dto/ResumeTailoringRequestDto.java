package com.resumeforge.analysis.dto;

import jakarta.validation.constraints.NotBlank;

public class ResumeTailoringRequestDto {

    @NotBlank
    private String resumeText;

    @NotBlank
    private String jobDescription;

    @NotBlank
    private String extractedKeywords;

    public ResumeTailoringRequestDto() {
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getExtractedKeywords() {
        return extractedKeywords;
    }

    public void setExtractedKeywords(String extractedKeywords) {
        this.extractedKeywords = extractedKeywords;
    }
}