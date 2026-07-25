package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class ResumeAnalysisPromptBuilder {

    public String buildPrompt(String resumeText, String jobDescription) {

        return """
                You are an expert ATS resume reviewer.

                Analyze the resume against the job description.

                Resume:
                %s

                Job Description:
                %s

                Return ONLY valid JSON.

                Do not return Markdown.
                Do not return explanations.
                Do not use code blocks.

                Use exactly this JSON structure:

                {
                  "overallScore": number,
                  "overallAssessment": "string",
                  "missingSkills": ["string"],
                  "summarySuggestions": ["string"],
                  "projectSuggestions": ["string"],
                  "atsSuggestions": ["string"]
                }
                """.formatted(resumeText, jobDescription);
    }
}