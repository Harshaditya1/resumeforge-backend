package com.resumeforge.analysis;

import com.resumeforge.analysis.dto.AnalysisRequestDto;
import com.resumeforge.analysis.dto.AnalysisResponseDto;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionRepository;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AnalysisService {

    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final KeywordExtractorService keywordExtractorService;

    public AnalysisService(
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            KeywordExtractorService keywordExtractorService) {

        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.keywordExtractorService = keywordExtractorService;
    }

    public AnalysisResponseDto analyze(AnalysisRequestDto request) {

        Resume resume = resumeRepository.findById(request.getResumeId())
                .orElseThrow(() -> new IllegalArgumentException("Resume not found with id: " + request.getResumeId()));

        JobDescription jobDescription = jobDescriptionRepository
                .findById(request.getJobDescriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Job Description not found with id: " + request.getJobDescriptionId()));

        Set<String> resumeKeywords =
                keywordExtractorService.extractKeywords(resume.getExtractedText());

        Set<String> jobKeywords =
                keywordExtractorService.extractKeywords(jobDescription.getContent());

        return AnalysisResponseDto.builder()
                .resumeKeywords(resumeKeywords)
                .jobKeywords(jobKeywords)
                .build();
    }
}