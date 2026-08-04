package com.resumeforge.ai;

import com.resumeforge.ai.prompt.ResumeTailoringPromptBuilder;
import com.resumeforge.analysis.dto.AIResumeTailoringResponseDto;
import com.resumeforge.exception.ResourceNotFoundException;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionRepository;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeRepository;
import com.resumeforge.tailoring.TailoredResume;
import com.resumeforge.tailoring.TailoredResumeService;
import org.springframework.stereotype.Service;

@Service
public class ResumeTailoringService {

    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final ResumeTailoringPromptBuilder promptBuilder;
    private final AiClientService aiClientService;
    private final TailoredResumeService tailoredResumeService;

    public ResumeTailoringService(
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            ResumeTailoringPromptBuilder promptBuilder,
            AiClientService aiClientService,
            TailoredResumeService tailoredResumeService
    ) {
        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
        this.tailoredResumeService = tailoredResumeService;
    }

    public AIResumeTailoringResponseDto tailorResume(
            Long resumeId,
            Long jobDescriptionId
    ) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume not found with id: " + resumeId
                        )
                );

        JobDescription jobDescription = jobDescriptionRepository.findById(jobDescriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job Description not found with id: " + jobDescriptionId
                        )
                );

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