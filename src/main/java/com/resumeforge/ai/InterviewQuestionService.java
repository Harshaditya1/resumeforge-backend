package com.resumeforge.ai;

import com.resumeforge.ai.prompt.InterviewQuestionPromptBuilder;
import com.resumeforge.analysis.dto.AIInterviewQuestionResponseDto;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionService;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeService;
import org.springframework.stereotype.Service;

@Service
public class InterviewQuestionService {

    private final ResumeService resumeService;
    private final JobDescriptionService jobDescriptionService;
    private final InterviewQuestionPromptBuilder promptBuilder;
    private final AiClientService aiClientService;

    public InterviewQuestionService(
            ResumeService resumeService,
            JobDescriptionService jobDescriptionService,
            InterviewQuestionPromptBuilder promptBuilder,
            AiClientService aiClientService
    ) {
        this.resumeService = resumeService;
        this.jobDescriptionService = jobDescriptionService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
    }

    public AIInterviewQuestionResponseDto generateInterviewQuestions(
            Long resumeId,
            Long jobDescriptionId
    ) {

        Resume resume = resumeService.getResumeById(resumeId);

        JobDescription jobDescription =
                jobDescriptionService.getJobDescriptionById(jobDescriptionId);

        String prompt = promptBuilder.buildPrompt(
                resume.getExtractedText(),
                jobDescription.getContent()
        );

        return aiClientService.ask(
                prompt,
                AIInterviewQuestionResponseDto.class
        );
    }
}