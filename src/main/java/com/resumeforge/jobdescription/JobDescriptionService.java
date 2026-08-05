package com.resumeforge.jobdescription;

import com.resumeforge.analysis.KeywordExtractorService;
import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.exception.ResourceNotFoundException;
import com.resumeforge.jobdescription.dto.JobDescriptionRequestDto;
import com.resumeforge.jobdescription.dto.JobDescriptionResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobDescriptionService {

    private final JobDescriptionRepository jobDescriptionRepository;
    private final KeywordExtractorService keywordExtractorService;
    private final CurrentUserService currentUserService;

    public JobDescriptionService(
            JobDescriptionRepository jobDescriptionRepository,
            KeywordExtractorService keywordExtractorService,
            CurrentUserService currentUserService
    ) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.keywordExtractorService = keywordExtractorService;
        this.currentUserService = currentUserService;
    }

    public JobDescriptionResponseDto saveJobDescription(JobDescriptionRequestDto request) {

        User currentUser = currentUserService.getCurrentUser();

        String extractedKeywords = String.join(
                ", ",
                keywordExtractorService.extractKeywords(request.getContent())
        );

        JobDescription jobDescription = JobDescription.builder()
                .user(currentUser)
                .content(request.getContent())
                .extractedKeywords(extractedKeywords)
                .createdAt(LocalDateTime.now())
                .build();

        JobDescription savedJobDescription = jobDescriptionRepository.save(jobDescription);

        return JobDescriptionResponseDto.builder()
                .id(savedJobDescription.getId())
                .content(savedJobDescription.getContent())
                .extractedKeywords(savedJobDescription.getExtractedKeywords())
                .createdAt(savedJobDescription.getCreatedAt())
                .build();
    }

    public JobDescription getJobDescriptionById(Long jobDescriptionId) {

        Long userId = currentUserService.getCurrentUser().getId();

        return jobDescriptionRepository
                .findByIdAndUserId(jobDescriptionId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job Description not found with id: " + jobDescriptionId
                        )
                );
    }
}