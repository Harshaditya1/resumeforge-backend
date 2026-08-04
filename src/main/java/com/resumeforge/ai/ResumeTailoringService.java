package com.resumeforge.ai;

import com.resumeforge.ai.prompt.ResumeTailoringPromptBuilder;
import com.resumeforge.analysis.dto.AIResumeTailoringResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ResumeTailoringService {

    private final AiClientService aiClientService;
    private final ResumeTailoringPromptBuilder promptBuilder;

    public ResumeTailoringService(
            AiClientService aiClientService,
            ResumeTailoringPromptBuilder promptBuilder
    ) {
        this.aiClientService = aiClientService;
        this.promptBuilder = promptBuilder;
    }

    public AIResumeTailoringResponseDto tailorResume(
            String resumeText,
            String jobDescription,
            String extractedKeywords
    ) {

        String prompt = promptBuilder.buildPrompt(
                resumeText,
                jobDescription,
                extractedKeywords
        );

        return aiClientService.ask(
                prompt,
                AIResumeTailoringResponseDto.class
        );
    }
}