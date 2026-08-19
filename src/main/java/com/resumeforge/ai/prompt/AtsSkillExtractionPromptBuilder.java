package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class AtsSkillExtractionPromptBuilder {

    public String buildPrompt(String resumeText) {

        return """
You are an expert ATS parser.

Your ONLY task is to extract technical skills from the resume.

IMPORTANT RULES

1. Return ONLY valid JSON.
2. Do NOT return Markdown.
3. Do NOT wrap JSON inside ``` blocks.
4. Do NOT explain anything.
5. Do NOT invent skills.
6. Remove duplicate skills.
7. Preserve official technology names.

IGNORE COMPLETELY

- Name
- Email
- Phone Number
- Address
- LinkedIn
- GitHub URL
- College Name
- University Name
- CGPA
- Percentage
- Marks
- Dates
- Years
- Company Names
- Project Titles
- Awards
- Achievements
- Hobbies
- Interests
- Languages Spoken
- Generic English Words

Return JSON EXACTLY in this format:

{
  "languages": [],
  "frameworks": [],
  "libraries": [],
  "databases": [],
  "cloud": [],
  "devOps": [],
  "testing": [],
  "messaging": [],
  "ai": [],
  "tools": [],
  "versionControl": [],
  "operatingSystems": [],
  "concepts": [],
  "softSkills": []
}

Resume:

%s
""".formatted(resumeText);

    }
}