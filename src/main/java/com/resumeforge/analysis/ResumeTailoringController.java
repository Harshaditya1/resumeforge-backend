package com.resumeforge.analysis;

import com.resumeforge.ai.InterviewQuestionService;
import com.resumeforge.ai.ResumeGenerationService;
import com.resumeforge.ai.ResumeTailoringService;
import com.resumeforge.analysis.dto.AIInterviewQuestionResponseDto;
import com.resumeforge.analysis.dto.AIResumeGenerationResponseDto;
import com.resumeforge.analysis.dto.AIResumeTailoringResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resume-tailoring")
@CrossOrigin(origins = "http://localhost:5173")
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

    /**
     * AI Resume Tailoring
     * Uses latest uploaded Resume and latest Job Description
     */
    @PostMapping
    public AIResumeTailoringResponseDto tailorResume() {

        return resumeTailoringService.tailorResume();
    }

    /**
     * Generate Final Resume
     * (We'll refactor ResumeGenerationService next)
     */
    @PostMapping("/generate")
    public AIResumeGenerationResponseDto generateResume() {

        return resumeGenerationService.generateResume();
    }

    /**
     * Generate Interview Questions
     * (We'll refactor InterviewQuestionService next)
     */
    @PostMapping("/interview-questions")
    public AIInterviewQuestionResponseDto generateInterviewQuestions() {

        return interviewQuestionService.generateInterviewQuestions();
    }
}