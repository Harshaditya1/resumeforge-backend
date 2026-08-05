package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class InterviewQuestionPromptBuilder {

    public String buildPrompt(String resumeText, String jobDescription) {

        return """
                You are an experienced Technical Interviewer.

                Your task is to generate interview questions based ONLY on:

                1. Candidate Resume
                2. Job Description

                STRICT RULES:

                1. Never invent skills that are not present in the resume.
                2. Never assume fake projects or work experience.
                3. Technical questions should focus on technologies present in both the resume and job description.
                4. HR questions should evaluate motivation, communication, and career goals.
                5. Behavioral questions should assess teamwork, problem solving, ownership, and learning ability.
                6. Generate exactly:
                   - 10 Technical Questions
                   - 5 HR Questions
                   - 5 Behavioral Questions
                7. Return ONLY valid JSON.
                8. Do not use Markdown.
                9. Do not include explanations.

                Resume:

                %s

                Job Description:

                %s

                Return exactly this JSON:

                {
                  "technicalQuestions": [
                    "string"
                  ],
                  "hrQuestions": [
                    "string"
                  ],
                  "behavioralQuestions": [
                    "string"
                  ]
                }
                """.formatted(resumeText, jobDescription);
    }
}