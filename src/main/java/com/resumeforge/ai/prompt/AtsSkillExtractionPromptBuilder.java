package com.resumeforge.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class AtsSkillExtractionPromptBuilder {

    public String buildPrompt(String resumeText) {

        return """
You are an ATS (Applicant Tracking System) resume parser.

Your task is to extract ONLY technical skills from the resume.

Return ONLY valid JSON.

Do NOT return markdown.

Do NOT return explanations.

Do NOT wrap the JSON inside ```.

Ignore completely:

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
- Years
- Dates
- Birth Date
- Gender
- Languages spoken
- Hobbies
- Interests
- Awards
- Certifications that do not mention technologies
- Project names unless they represent technologies
- Company names
- Generic English words

Extract and categorize technical skills into the following JSON structure.

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

Rules:

1. Put every skill into the most appropriate category.

2. Do not duplicate skills.

3. Keep original technology names.

4. Return empty arrays when a category has no skills.

Resume:

%s
""".formatted(resumeText);

    }
}