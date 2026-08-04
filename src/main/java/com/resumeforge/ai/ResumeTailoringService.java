package com.resumeforge.ai;

import com.resumeforge.ai.prompt.ResumeTailoringPromptBuilder;
import com.resumeforge.analysis.dto.AIResumeTailoringResponseDto;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionService;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeService;
import com.resumeforge.tailoring.TailoredResume;
import com.resumeforge.tailoring.TailoredResumeService;
import org.springframework.stereotype.Service;

@Service
public class ResumeTailoringService {

    private final ResumeService resumeService;
    private final JobDescriptionService jobDescriptionService;
    private final ResumeTailoringPromptBuilder promptBuilder;
    private final AiClientService aiClientService;
    private final TailoredResumeService tailoredResumeService;

    public ResumeTailoringService(
            ResumeService resumeService,
            JobDescriptionService jobDescriptionService,
            ResumeTailoringPromptBuilder promptBuilder,
            AiClientService aiClientService,
            TailoredResumeService tailoredResumeService
    ) {
        this.resumeService = resumeService;
        this.jobDescriptionService = jobDescriptionService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
        this.tailoredResumeService = tailoredResumeService;
    }

    public AIResumeTailoringResponseDto tailorResume(
            Long resumeId,
            Long jobDescriptionId
    ) {

        Resume resume = resumeService.getResumeById(resumeId);

        JobDescription jobDescription =
                jobDescriptionService.getJobDescriptionById(jobDescriptionId);

        String prompt = promptBuilder.buildPrompt(
                resume.getExtractedText(),
                jobDescription.getContent(),
                jobDescription.getExtractedKeywords()
        );

        AIResumeTailoringResponseDto response = aiClientService.ask(
                prompt,
                AIResumeTailoringResponseDto.class
        );

        TailoredResume tailoredResume = TailoredResume.builder()
                .resume(resume)
                .jobDescription(jobDescription)
                .professionalSummary(response.getProfessionalSummary())
                .skills(String.join(", ", response.getSkills()))
                .experienceSuggestions(String.join("\n", response.getExperienceSuggestions()))
                .projectSuggestions(String.join("\n", response.getProjectSuggestions()))
                .missingKeywords(String.join(", ", response.getMissingKeywords()))
                .overallSuggestions(String.join("\n", response.getOverallSuggestions()))
                .build();

        tailoredResumeService.save(tailoredResume);

        return response;
    }
}