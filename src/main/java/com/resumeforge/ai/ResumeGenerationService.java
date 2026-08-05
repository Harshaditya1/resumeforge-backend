package com.resumeforge.ai;

import com.resumeforge.ai.prompt.ResumeGenerationPromptBuilder;
import com.resumeforge.analysis.dto.AIResumeGenerationResponseDto;
import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.generatedresume.GeneratedResume;
import com.resumeforge.generatedresume.GeneratedResumeService;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionService;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeService;
import org.springframework.stereotype.Service;

@Service
public class ResumeGenerationService {

    private final ResumeService resumeService;
    private final JobDescriptionService jobDescriptionService;
    private final ResumeGenerationPromptBuilder promptBuilder;
    private final AiClientService aiClientService;
    private final GeneratedResumeService generatedResumeService;
    private final CurrentUserService currentUserService;

    public ResumeGenerationService(
            ResumeService resumeService,
            JobDescriptionService jobDescriptionService,
            ResumeGenerationPromptBuilder promptBuilder,
            AiClientService aiClientService,
            GeneratedResumeService generatedResumeService,
            CurrentUserService currentUserService
    ) {
        this.resumeService = resumeService;
        this.jobDescriptionService = jobDescriptionService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
        this.generatedResumeService = generatedResumeService;
        this.currentUserService = currentUserService;
    }

    public AIResumeGenerationResponseDto generateResume(
            Long resumeId,
            Long jobDescriptionId
    ) {

        User currentUser = currentUserService.getCurrentUser();

        Resume resume = resumeService.getResumeById(resumeId);

        JobDescription jobDescription =
                jobDescriptionService.getJobDescriptionById(jobDescriptionId);

        String prompt = promptBuilder.buildPrompt(
                resume.getExtractedText(),
                jobDescription.getContent()
        );

        AIResumeGenerationResponseDto response =
                aiClientService.ask(
                        prompt,
                        AIResumeGenerationResponseDto.class
                );

        GeneratedResume generatedResume = GeneratedResume.builder()
                .user(currentUser)
                .resume(resume)
                .jobDescription(jobDescription)
                .generatedResume(response.getGeneratedResume())
                .build();

        generatedResumeService.save(generatedResume);

        return response;
    }
}