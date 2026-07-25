package com.resumeforge.analysis;

import com.resumeforge.analysis.dto.AnalysisRequestDto;
import com.resumeforge.analysis.dto.AnalysisResponseDto;
import com.resumeforge.analysis.dto.AtsReportDto;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionRepository;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AnalysisService {

    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final KeywordExtractorService keywordExtractorService;
    private final AtsReportService atsReportService;

    public AnalysisService(
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            KeywordExtractorService keywordExtractorService,
            AtsReportService atsReportService) {

        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.keywordExtractorService = keywordExtractorService;
        this.atsReportService = atsReportService;
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

        // Extract keywords
        Set<String> resumeKeywords =
                keywordExtractorService.extractKeywords(resume.getExtractedText());

        Set<String> jobKeywords =
                keywordExtractorService.extractKeywords(jobDescription.getContent());

        // Find matched keywords
        Set<String> matchedKeywords = new HashSet<>(resumeKeywords);
        matchedKeywords.retainAll(jobKeywords);

        // Find missing keywords
        Set<String> missingKeywords = new HashSet<>(jobKeywords);
        missingKeywords.removeAll(resumeKeywords);

        // Calculate ATS Match Percentage
        double matchPercentage = 0.0;

        if (!jobKeywords.isEmpty()) {
            matchPercentage = ((double) matchedKeywords.size() / jobKeywords.size()) * 100;
            matchPercentage = Math.round(matchPercentage * 100.0) / 100.0;
        }

        // Generate ATS Report
        AtsReportDto report = atsReportService.generateReport(
                matchPercentage,
                new ArrayList<>(matchedKeywords),
                new ArrayList<>(missingKeywords)
        );

        // Build Response
        return AnalysisResponseDto.builder()
                .resumeKeywords(new ArrayList<>(resumeKeywords))
                .jobDescriptionKeywords(new ArrayList<>(jobKeywords))
                .matchedKeywords(new ArrayList<>(matchedKeywords))
                .missingKeywords(new ArrayList<>(missingKeywords))
                .matchPercentage(matchPercentage)
                .report(report)
                .build();
    }
}