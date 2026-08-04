package com.resumeforge.analysis;

import com.resumeforge.ai.AiAnalysisService;
import com.resumeforge.analysis.dto.AiAnalysisResponseDto;
import com.resumeforge.analysis.dto.AnalysisRequestDto;
import com.resumeforge.analysis.dto.AnalysisResponseDto;
import com.resumeforge.analysis.dto.AtsReportDto;
import com.resumeforge.analysis.dto.ResumeImprovementDto;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.jobdescription.JobDescriptionRepository;
import com.resumeforge.resume.Resume;
import com.resumeforge.resume.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final KeywordExtractorService keywordExtractorService;
    private final AtsReportService atsReportService;
    private final ResumeImprovementService resumeImprovementService;
    private final AiAnalysisService aiAnalysisService;

    public AnalysisService(
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            KeywordExtractorService keywordExtractorService,
            AtsReportService atsReportService,
            ResumeImprovementService resumeImprovementService,
            AiAnalysisService aiAnalysisService) {

        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.keywordExtractorService = keywordExtractorService;
        this.atsReportService = atsReportService;
        this.resumeImprovementService = resumeImprovementService;
        this.aiAnalysisService = aiAnalysisService;
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

        Set<String> matchedKeywords = new HashSet<>(resumeKeywords);
        matchedKeywords.retainAll(jobKeywords);

        Set<String> missingKeywords = new HashSet<>(jobKeywords);
        missingKeywords.removeAll(resumeKeywords);

        double matchPercentage = 0.0;

        if (!jobKeywords.isEmpty()) {
            matchPercentage = ((double) matchedKeywords.size() / jobKeywords.size()) * 100;
            matchPercentage = Math.round(matchPercentage * 100.0) / 100.0;
        }

        AtsReportDto report = atsReportService.generateReport(
                matchPercentage,
                new ArrayList<>(matchedKeywords),
                new ArrayList<>(missingKeywords)
        );

        ResumeImprovementDto improvement =
                resumeImprovementService.generateSuggestions(
                        new ArrayList<>(missingKeywords)
                );

        AiAnalysisResponseDto aiAnalysis = null;

        try {
            aiAnalysis = aiAnalysisService.analyzeResume(
                    resume.getExtractedText(),
                    jobDescription.getContent()
            );
        } catch (Exception ex) {
            logger.error("Gemini AI analysis failed. ATS analysis will continue.", ex);
        }

        return AnalysisResponseDto.builder()
                .resumeKeywords(new ArrayList<>(resumeKeywords))
                .jobDescriptionKeywords(new ArrayList<>(jobKeywords))
                .matchedKeywords(new ArrayList<>(matchedKeywords))
                .missingKeywords(new ArrayList<>(missingKeywords))
                .matchPercentage(matchPercentage)
                .report(report)
                .improvement(improvement)
                .aiAnalysis(aiAnalysis)
                .build();
    }
}