package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class ResumeGenerationPromptBuilder {

    public String buildPrompt(
            String resumeText,
            String jobDescription
    ) {

        return """
                You are an expert ATS Resume Writer.

                Your task is to rewrite the candidate's resume so that it matches the job description while remaining truthful.

                Rules:
                - Never invent experience.
                - Never invent projects.
                - Never invent skills.
                - Improve wording professionally.
                - Optimize for ATS keywords.
                - Preserve factual information.
                - Produce a complete professional resume.
                - Return only the resume text.
                
                -------------------------
                ORIGINAL RESUME
                -------------------------
                %s

                -------------------------
                JOB DESCRIPTION
                -------------------------
                %s
                """.formatted(
                resumeText,
                jobDescription
        );
    }
}