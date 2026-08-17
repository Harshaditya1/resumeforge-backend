package com.resumeforge.ai;

import com.resumeforge.ai.prompt.ResumeGenerationPromptBuilder;
import com.resumeforge.analysis.dto.AIResumeGenerationResponseDto;
import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.generatedresume.GeneratedResume;
import com.resumeforge.generatedresume.GeneratedResumeService;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.resume.Resume;
import org.springframework.stereotype.Service;

@Service
public class ResumeGenerationService {

    private final CurrentResumeContextService currentResumeContextService;
    private final ResumeGenerationPromptBuilder promptBuilder;
    private final AiClientService aiClientService;
    private final GeneratedResumeService generatedResumeService;
    private final CurrentUserService currentUserService;

    public ResumeGenerationService(
            CurrentResumeContextService currentResumeContextService,
            ResumeGenerationPromptBuilder promptBuilder,
            AiClientService aiClientService,
            GeneratedResumeService generatedResumeService,
            CurrentUserService currentUserService
    ) {
        this.currentResumeContextService = currentResumeContextService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
        this.generatedResumeService = generatedResumeService;
        this.currentUserService = currentUserService;
    }

    public AIResumeGenerationResponseDto generateResume() {

        User currentUser =
                currentUserService.getCurrentUser();

        Resume resume =
                currentResumeContextService.getLatestResume();

        JobDescription jobDescription =
                currentResumeContextService
                        .getLatestJobDescription();

        String prompt =
                promptBuilder.buildPrompt(
                        resume.getExtractedText(),
                        jobDescription.getContent()
                );

        AIResumeGenerationResponseDto response =
                aiClientService.ask(
                        prompt,
                        AIResumeGenerationResponseDto.class
                );
        GeneratedResume generatedResume =
                GeneratedResume.builder()
                        .user(currentUser)
                        .resume(resume)
                        .jobDescription(jobDescription)
                        .generatedResume(
                                response.getGeneratedResume()
                        )
                        .build();

        generatedResumeService.save(
                generatedResume
        );

        return response;
    }
}