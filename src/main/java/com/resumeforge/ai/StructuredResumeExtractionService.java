package com.resumeforge.ai;

import com.resumeforge.ai.prompt.StructuredResumePromptBuilder;
import com.resumeforge.analysis.model.StructuredResume;
import com.resumeforge.resume.Resume;
import org.springframework.stereotype.Service;

@Service
public class StructuredResumeExtractionService {

    private final CurrentResumeContextService currentResumeContextService;
    private final StructuredResumePromptBuilder promptBuilder;
    private final AiClientService aiClientService;

    public StructuredResumeExtractionService(
            CurrentResumeContextService currentResumeContextService,
            StructuredResumePromptBuilder promptBuilder,
            AiClientService aiClientService
    ) {
        this.currentResumeContextService = currentResumeContextService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
    }

    public StructuredResume extractStructuredResume() {

        Resume resume =
                currentResumeContextService.getLatestResume();

        String prompt =
                promptBuilder.buildPrompt(
                        resume.getExtractedText()
                );

        return aiClientService.ask(
                prompt,
                StructuredResume.class
        );
    }
}