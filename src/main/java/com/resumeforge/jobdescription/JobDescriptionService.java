package com.resumeforge.jobdescription;

import com.resumeforge.analysis.KeywordExtractorService;
import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.exception.ResourceNotFoundException;
import com.resumeforge.jobdescription.dto.JobDescriptionRequestDto;
import com.resumeforge.jobdescription.dto.JobDescriptionResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.resumeforge.jobdescription.mapper.JobDescriptionMapper;

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

        return JobDescriptionMapper.toResponseDto(savedJobDescription);
    }

    public List<JobDescriptionResponseDto> getAllJobDescriptions() {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        return jobDescriptionRepository
                .findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(JobDescriptionMapper::toResponseDto)
                .toList();
    }

    public JobDescriptionResponseDto getLatestJobDescription() {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        JobDescription jobDescription = jobDescriptionRepository
                .findFirstByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No Job Description found."
                        )
                );

        return JobDescriptionMapper.toResponseDto(jobDescription);
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
    public void deleteJobDescription(Long jobDescriptionId) {

        JobDescription jobDescription =
                getJobDescriptionById(jobDescriptionId);

        jobDescriptionRepository.delete(jobDescription);
    }
}