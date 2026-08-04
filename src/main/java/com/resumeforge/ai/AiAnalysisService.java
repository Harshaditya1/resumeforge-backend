package com.resumeforge.ai;

import com.resumeforge.ai.prompt.ResumeAnalysisPromptBuilder;
import com.resumeforge.analysis.dto.AiAnalysisResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AiAnalysisService {

    private final AiClientService aiClientService;
    private final ResumeAnalysisPromptBuilder promptBuilder;

    public AiAnalysisService(
            AiClientService aiClientService,
            ResumeAnalysisPromptBuilder promptBuilder) {

        this.aiClientService = aiClientService;
        this.promptBuilder = promptBuilder;
    }

    public AiAnalysisResponseDto analyzeResume(String resumeText, String jobDescription) {

        String prompt = promptBuilder.buildPrompt(
                resumeText,
                jobDescription
        );

        return aiClientService.ask(
                prompt,
                AiAnalysisResponseDto.class
        );
    }
}