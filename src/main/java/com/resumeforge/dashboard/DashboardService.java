package com.resumeforge.dashboard;

import com.resumeforge.generatedresume.GeneratedResumeRepository;
import com.resumeforge.jobdescription.JobDescriptionRepository;
import com.resumeforge.resume.ResumeRepository;
import com.resumeforge.tailoring.TailoredResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ResumeRepository resumeRepository;
    private final GeneratedResumeRepository generatedResumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final TailoredResumeRepository tailoredResumeRepository;

    public DashboardSummaryDTO getDashboardSummary() {

        return DashboardSummaryDTO.builder()
                .totalResumes(resumeRepository.count())
                .totalGeneratedResumes(generatedResumeRepository.count())
                .totalJobDescriptions(jobDescriptionRepository.count())
                .totalTailoredResumes(tailoredResumeRepository.count())
                .build();
    }
}