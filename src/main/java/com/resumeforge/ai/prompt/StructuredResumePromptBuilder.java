package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class StructuredResumePromptBuilder {

    public String buildPrompt(String resumeText) {

        return """
You are an expert resume parser.

Your task is to convert the following resume into structured JSON.

IMPORTANT RULES

1. Return ONLY valid JSON.
2. Do NOT return Markdown.
3. Do NOT wrap JSON inside ``` blocks.
4. Do NOT explain anything.
5. Use empty arrays when data is unavailable.
6. Preserve all important resume information.
7. Do not invent information.

Return JSON in exactly this format:

{
  "name": "",
  "education": [],
  "experience": [],
  "projects": [],
  "technicalSkills": [],
  "softSkills": [],
  "certifications": [],
  "achievements": [],
  "leadership": []
}

Resume:

%s
""".formatted(resumeText);

    }
}