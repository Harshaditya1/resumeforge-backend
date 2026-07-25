package com.resumeforge.jobdescription;

import com.resumeforge.jobdescription.dto.JobDescriptionRequestDto;
import com.resumeforge.jobdescription.dto.JobDescriptionResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobDescriptionService {

    private final JobDescriptionRepository jobDescriptionRepository;

    public JobDescriptionService(JobDescriptionRepository jobDescriptionRepository) {
        this.jobDescriptionRepository = jobDescriptionRepository;
    }

    public JobDescriptionResponseDto saveJobDescription(JobDescriptionRequestDto request) {

        JobDescription jobDescription = JobDescription.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        JobDescription savedJobDescription = jobDescriptionRepository.save(jobDescription);

        return JobDescriptionResponseDto.builder()
                .id(savedJobDescription.getId())
                .content(savedJobDescription.getContent())
                .createdAt(savedJobDescription.getCreatedAt())
                .build();
    }
}