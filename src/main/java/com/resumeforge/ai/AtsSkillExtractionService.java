package com.resumeforge.ai;

import com.resumeforge.ai.prompt.AtsSkillExtractionPromptBuilder;
import com.resumeforge.analysis.model.AtsSkillProfile;
import com.resumeforge.resume.Resume;
import org.springframework.stereotype.Service;

@Service
public class AtsSkillExtractionService {

    private final CurrentResumeContextService currentResumeContextService;
    private final AtsSkillExtractionPromptBuilder promptBuilder;
    private final AiClientService aiClientService;

    public AtsSkillExtractionService(
            CurrentResumeContextService currentResumeContextService,
            AtsSkillExtractionPromptBuilder promptBuilder,
            AiClientService aiClientService
    ) {
        this.currentResumeContextService = currentResumeContextService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
    }

    public AtsSkillProfile extractSkills() {

        Resume resume =
                currentResumeContextService.getLatestResume();

        String prompt =
                promptBuilder.buildPrompt(
                        resume.getExtractedText()
                );

        return aiClientService.ask(
                prompt,
                AtsSkillProfile.class
        );
    }
}