package com.resumeforge.analysis;

import com.resumeforge.ai.ResumeTailoringService;
import com.resumeforge.analysis.dto.AIResumeTailoringResponseDto;
import com.resumeforge.analysis.dto.ResumeTailoringRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resume-tailoring")
public class ResumeTailoringController {

    private final ResumeTailoringService resumeTailoringService;

    public ResumeTailoringController(ResumeTailoringService resumeTailoringService) {
        this.resumeTailoringService = resumeTailoringService;
    }

    @PostMapping
    public AIResumeTailoringResponseDto tailorResume(
            @Valid @RequestBody ResumeTailoringRequestDto request
    ) {

        return resumeTailoringService.tailorResume(
                request.getResumeText(),
                request.getJobDescription(),
                request.getExtractedKeywords()
        );
    }
}