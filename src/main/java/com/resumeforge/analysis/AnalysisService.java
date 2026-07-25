package com.resumeforge.analysis;

import com.resumeforge.analysis.dto.AnalysisRequestDto;
import com.resumeforge.analysis.dto.AnalysisResponseDto;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionRepository;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Resume not found with id: " + request.getResumeId()));

        JobDescription jobDescription = jobDescriptionRepository
                .findById(request.getJobDescriptionId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Job Description not found with id: " + request.getJobDescriptionId()));

        Set<String> resumeKeywords =
                keywordExtractorService.extractKeywords(resume.getExtractedText());

        Set<String> jobKeywords =
                keywordExtractorService.extractKeywords(jobDescription.getContent());

        Set<String> matchedKeywords = new java.util.HashSet<>(resumeKeywords);
        matchedKeywords.retainAll(jobKeywords);

        Set<String> missingKeywords = new java.util.HashSet<>(jobKeywords);
        missingKeywords.removeAll(resumeKeywords);

        double matchPercentage = 0.0;

        if (!jobKeywords.isEmpty()) {
            matchPercentage = ((double) matchedKeywords.size() / jobKeywords.size()) * 100;
            matchPercentage = Math.round(matchPercentage * 100.0) / 100.0;
        }

        return AnalysisResponseDto.builder()
                .resumeKeywords(new ArrayList<>(resumeKeywords))
                .jobDescriptionKeywords(new ArrayList<>(jobKeywords))
                .matchedKeywords(new ArrayList<>(matchedKeywords))
                .missingKeywords(new ArrayList<>(missingKeywords))
                .matchPercentage(matchPercentage)
                .build();
    }
}