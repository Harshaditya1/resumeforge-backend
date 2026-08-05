package com.resumeforge.analysis.dto;

import jakarta.validation.constraints.NotNull;

public class InterviewQuestionRequestDto {

    @NotNull(message = "Resume ID is required")
    private Long resumeId;

    @NotNull(message = "Job Description ID is required")
    private Long jobDescriptionId;

    public InterviewQuestionRequestDto() {
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Long getJobDescriptionId() {
        return jobDescriptionId;
    }

    public void setJobDescriptionId(Long jobDescriptionId) {
        this.jobDescriptionId = jobDescriptionId;
    }
}