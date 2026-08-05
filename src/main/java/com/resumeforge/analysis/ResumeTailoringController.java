package com.resumeforge.analysis;

import com.resumeforge.ai.InterviewQuestionService;
import com.resumeforge.ai.ResumeGenerationService;
import com.resumeforge.ai.ResumeTailoringService;
import com.resumeforge.analysis.dto.AIInterviewQuestionResponseDto;
import com.resumeforge.analysis.dto.AIResumeGenerationResponseDto;
import com.resumeforge.analysis.dto.AIResumeTailoringResponseDto;
import com.resumeforge.analysis.dto.GenerateResumeRequestDto;
import com.resumeforge.analysis.dto.InterviewQuestionRequestDto;
import com.resumeforge.analysis.dto.ResumeTailoringRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resume-tailoring")
public class ResumeTailoringController {

    private final ResumeTailoringService resumeTailoringService;
    private final ResumeGenerationService resumeGenerationService;
    private final InterviewQuestionService interviewQuestionService;

    public ResumeTailoringController(
            ResumeTailoringService resumeTailoringService,
            ResumeGenerationService resumeGenerationService,
            InterviewQuestionService interviewQuestionService
    ) {
        this.resumeTailoringService = resumeTailoringService;
        this.resumeGenerationService = resumeGenerationService;
        this.interviewQuestionService = interviewQuestionService;
    }

    @PostMapping
    public AIResumeTailoringResponseDto tailorResume(
            @Valid @RequestBody ResumeTailoringRequestDto request
    ) {
        return resumeTailoringService.tailorResume(
                request.getResumeId(),
                request.getJobDescriptionId()
        );
    }

    @PostMapping("/generate")
    public AIResumeGenerationResponseDto generateResume(
            @Valid @RequestBody GenerateResumeRequestDto request
    ) {
        return resumeGenerationService.generateResume(
                request.getResumeId(),
                request.getJobDescriptionId()
        );
    }

    @PostMapping("/interview-questions")
    public AIInterviewQuestionResponseDto generateInterviewQuestions(
            @Valid @RequestBody InterviewQuestionRequestDto request
    ) {
        return interviewQuestionService.generateInterviewQuestions(
                request.getResumeId(),
                request.getJobDescriptionId()
        );
    }
}