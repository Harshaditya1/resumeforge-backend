package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class ResumeTailoringPromptBuilder {

    public String buildPrompt(String resumeText,
                              String jobDescription,
                              String extractedKeywords) {

        return """
You are an expert ATS Resume Tailoring Assistant.

Your task is to improve the resume according to the given Job Description.

Rules:

- Never invent fake experience.
- Never invent fake projects.
- Never change education.
- Never change company names.
- Only rewrite wording.
- Add missing skills only if already reflected somewhere in the resume.
- Improve ATS score.
- Keep professional formatting.

Return ONLY valid JSON.

JSON Structure:

{
  "professionalSummary":"",
  "skills":[
  ],
  "experienceSuggestions":[
  ],
  "projectSuggestions":[
  ],
  "missingKeywords":[
  ],
  "overallSuggestions":[
  ]
}

Resume:

%s

Job Description:

%s

Extracted Keywords:

%s
""".formatted(
                resumeText,
                jobDescription,
                extractedKeywords
        );
    }
}