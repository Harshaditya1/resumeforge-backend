package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class ResumeAnalysisPromptBuilder {

    public String buildPrompt(String resumeText, String jobDescription) {

        return """
                You are an expert ATS Resume Reviewer and Career Coach.

                Your task is to analyze the candidate's resume against the provided job description.

                STRICT RULES (MUST FOLLOW):

                1. NEVER invent projects.
                2. NEVER invent work experience.
                3. NEVER invent internships.
                4. NEVER invent certifications.
                5. NEVER invent technical skills.
                6. NEVER claim the candidate knows something that is not present in the resume.
                7. Analyze ONLY the information available in the resume.
                8. If important skills are missing, recommend learning them or adding them ONLY if the candidate genuinely possesses them.
                9. Never encourage lying, exaggeration, or fake experience.
                10. Recommendations must be ATS-friendly, professional, realistic, and actionable.
                11. Do not recommend adding fake projects.
                12. Do not recommend adding fake certifications.
                13. Do not recommend adding fake achievements.
                14. Do not modify personal details such as name, email, phone number, education, or dates.
                15. Return ONLY valid JSON.

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