package com.resumeforge.analysis.dto;

import java.util.List;

public class AIInterviewQuestionResponseDto {

    private List<String> technicalQuestions;

    private List<String> hrQuestions;

    private List<String> behavioralQuestions;

    public AIInterviewQuestionResponseDto() {
    }

    public List<String> getTechnicalQuestions() {
        return technicalQuestions;
    }

    public void setTechnicalQuestions(List<String> technicalQuestions) {
        this.technicalQuestions = technicalQuestions;
    }

    public List<String> getHrQuestions() {
        return hrQuestions;
    }

    public void setHrQuestions(List<String> hrQuestions) {
        this.hrQuestions = hrQuestions;
    }

    public List<String> getBehavioralQuestions() {
        return behavioralQuestions;
    }

    public void setBehavioralQuestions(List<String> behavioralQuestions) {
        this.behavioralQuestions = behavioralQuestions;
    }
}