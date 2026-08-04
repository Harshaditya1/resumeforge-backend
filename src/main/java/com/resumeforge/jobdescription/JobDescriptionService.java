package com.resumeforge.jobdescription;

import com.resumeforge.analysis.KeywordExtractorService;
import com.resumeforge.jobdescription.dto.JobDescriptionRequestDto;
import com.resumeforge.jobdescription.dto.JobDescriptionResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobDescriptionService {

    private final JobDescriptionRepository jobDescriptionRepository;
    private final KeywordExtractorService keywordExtractorService;

    public JobDescriptionService(
            JobDescriptionRepository jobDescriptionRepository,
            KeywordExtractorService keywordExtractorService
    ) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.keywordExtractorService = keywordExtractorService;
    }

    public JobDescriptionResponseDto saveJobDescription(JobDescriptionRequestDto request) {

        String extractedKeywords = String.join(
                ", ",
                keywordExtractorService.extractKeywords(request.getContent())
        );

        JobDescription jobDescription = JobDescription.builder()
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
}