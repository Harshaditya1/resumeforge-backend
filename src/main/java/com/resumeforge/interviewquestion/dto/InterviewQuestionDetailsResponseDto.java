package com.resumeforge.interviewquestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewQuestionDetailsResponseDto {

    private Long id;

    private Long resumeId;

    private Long jobDescriptionId;

    /**
     * Complete AI generated interview questions
     */
    private String questions;

    private LocalDateTime createdAt;
}