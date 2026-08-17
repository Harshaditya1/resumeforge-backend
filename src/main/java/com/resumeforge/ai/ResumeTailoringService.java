package com.resumeforge.ai;

import com.resumeforge.ai.prompt.ResumeTailoringPromptBuilder;
import com.resumeforge.analysis.dto.AIResumeTailoringResponseDto;
import com.resumeforge.resume.Resume;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.tailoring.TailoredResume;
import com.resumeforge.tailoring.TailoredResumeService;
import org.springframework.stereotype.Service;

@Service
public class ResumeTailoringService {

    private final CurrentResumeContextService currentResumeContextService;
    private final ResumeTailoringPromptBuilder promptBuilder;
    private final AiClientService aiClientService;
    private final TailoredResumeService tailoredResumeService;

    public ResumeTailoringService(
            CurrentResumeContextService currentResumeContextService,
            ResumeTailoringPromptBuilder promptBuilder,
            AiClientService aiClientService,
            TailoredResumeService tailoredResumeService
    ) {
        this.currentResumeContextService = currentResumeContextService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
        this.tailoredResumeService = tailoredResumeService;
    }

    public AIResumeTailoringResponseDto tailorResume() {

        Resume resume =
                currentResumeContextService.getLatestResume();

        JobDescription jobDescription =
                currentResumeContextService
                        .getLatestJobDescription();

        String prompt =
                promptBuilder.buildPrompt(
                        resume.getExtractedText(),
                        jobDescription.getContent(),
                        jobDescription.getExtractedKeywords()
                );

        AIResumeTailoringResponseDto response =
                aiClientService.ask(
                        prompt,
                        AIResumeTailoringResponseDto.class
                );
        TailoredResume latest = null;

        try {
            latest = tailoredResumeService.getLatest(
                    resume.getId()
            );
        } catch (Exception ignored) {
            // First tailored resume
        }

        int nextVersion = 1;

        if (latest != null) {
            nextVersion = latest.getVersionNumber() + 1;
        }

        TailoredResume tailoredResume =
                TailoredResume.builder()

                        .user(resume.getUser())

                        .resume(resume)

                        .jobDescription(jobDescription)

                        .professionalSummary(
                                response.getProfessionalSummary())

                        .skills(
                                response.getSkills())

                        .experienceSuggestions(
                                response.getExperienceSuggestions())

                        .projectSuggestions(
                                response.getProjectSuggestions())

                        .missingKeywords(
                                response.getMissingKeywords())

                        .overallSuggestions(
                                response.getOverallSuggestions())

                        .versionNumber(nextVersion)

                        .approved(false)

                        .downloaded(false)

                        .build();

        tailoredResumeService.save(
                tailoredResume
        );

        return response;
    }
}