package com.resumeforge.analysis;

import com.resumeforge.ai.AiAnalysisService;
import com.resumeforge.analysis.dto.*;
import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.exception.ResourceNotFoundException;
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
import java.util.List;

@Service
public class AnalysisService {

    private static final Logger logger =
            LoggerFactory.getLogger(AnalysisService.class);

    private final AnalysisRepository analysisRepository;
    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final KeywordExtractorService keywordExtractorService;
    private final AtsReportService atsReportService;
    private final ResumeImprovementService resumeImprovementService;
    private final AiAnalysisService aiAnalysisService;
    private final CurrentUserService currentUserService;

    public AnalysisService(
            AnalysisRepository analysisRepository,
            ResumeRepository resumeRepository,
            JobDescriptionRepository jobDescriptionRepository,
            KeywordExtractorService keywordExtractorService,
            AtsReportService atsReportService,
            ResumeImprovementService resumeImprovementService,
            AiAnalysisService aiAnalysisService,
            CurrentUserService currentUserService) {

        this.analysisRepository = analysisRepository;
        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.keywordExtractorService = keywordExtractorService;
        this.atsReportService = atsReportService;
        this.resumeImprovementService = resumeImprovementService;
        this.aiAnalysisService = aiAnalysisService;
        this.currentUserService = currentUserService;
    }

    public AnalysisResponseDto analyze() {

        User currentUser =
                currentUserService.getCurrentUser();

        Resume resume =
                resumeRepository
                        .findFirstByUserIdOrderByUploadedAtDesc(
                                currentUser.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No resume uploaded."
                                ));

        JobDescription jobDescription =
                jobDescriptionRepository
                        .findFirstByUserIdOrderByCreatedAtDesc(
                                currentUser.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No Job Description found."
                                ));

        Set<String> resumeKeywords =
                keywordExtractorService.extractKeywords(
                        resume.getExtractedText()
                );

        Set<String> jobKeywords =
                keywordExtractorService.extractKeywords(
                        jobDescription.getContent()
                );

        Set<String> matchedKeywords =
                new HashSet<>(resumeKeywords);

        matchedKeywords.retainAll(jobKeywords);

        Set<String> missingKeywords =
                new HashSet<>(jobKeywords);

        missingKeywords.removeAll(resumeKeywords);

        double matchPercentage = 0.0;

        if (!jobKeywords.isEmpty()) {

            matchPercentage =
                    ((double) matchedKeywords.size()
                            / jobKeywords.size()) * 100;

            matchPercentage =
                    Math.round(matchPercentage * 100.0) / 100.0;
        }

        AtsReportDto report =
                atsReportService.generateReport(
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

            aiAnalysis =
                    aiAnalysisService.analyzeResume(
                            resume.getExtractedText(),
                            jobDescription.getContent()
                    );

        } catch (Exception ex) {

            logger.error(
                    "Gemini AI analysis failed.",
                    ex
            );
        }
        Analysis analysis = Analysis.builder()
                .user(currentUser)
                .resume(resume)
                .jobDescription(jobDescription)
                .matchPercentage(matchPercentage)

                .resumeKeywords(new ArrayList<>(resumeKeywords))
                .jobDescriptionKeywords(new ArrayList<>(jobKeywords))
                .matchedKeywords(new ArrayList<>(matchedKeywords))
                .missingKeywords(new ArrayList<>(missingKeywords))

                .overallAssessment(report.getOverallAssessment())
                .scoreCategory(report.getScoreCategory())
                .strengths(report.getStrengths())
                .improvements(report.getImprovements())
                .recommendation(report.getRecommendation())

                .build();

        if (aiAnalysis != null) {

            analysis.setAiOverallScore(
                    aiAnalysis.getOverallScore());

            analysis.setAiOverallAssessment(
                    aiAnalysis.getOverallAssessment());

            analysis.setAiMissingSkills(
                    aiAnalysis.getMissingSkills());

            analysis.setAiProjectSuggestions(
                    aiAnalysis.getProjectSuggestions());

            analysis.setAiSummarySuggestions(
                    aiAnalysis.getSummarySuggestions());

            analysis.setAiAtsSuggestions(
                    aiAnalysis.getAtsSuggestions());
        }

        analysisRepository.save(analysis);

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

    /**
     * Latest Analysis
     */
    public AnalysisResponseDto getLatestAnalysis() {

        User currentUser =
                currentUserService.getCurrentUser();

        Analysis analysis =
                analysisRepository
                        .findFirstByUserIdOrderByCreatedAtDesc(
                                currentUser.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No analysis found."
                                ));

        return mapToResponse(analysis);
    }
    /**
     * Analysis History
     */
    public List<AnalysisResponseDto> getAnalysisHistory() {

        User currentUser =
                currentUserService.getCurrentUser();

        List<Analysis> analyses =
                analysisRepository
                        .findAllByUserIdOrderByCreatedAtDesc(
                                currentUser.getId()
                        );

        return analyses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Entity -> Response DTO
     */
    private AnalysisResponseDto mapToResponse(
            Analysis analysis
    ) {

        AtsReportDto report = AtsReportDto.builder()
                .overallAssessment(
                        analysis.getOverallAssessment())
                .scoreCategory(
                        analysis.getScoreCategory())
                .strengths(
                        analysis.getStrengths())
                .improvements(
                        analysis.getImprovements())
                .recommendation(
                        analysis.getRecommendation())
                .build();

        ResumeImprovementDto improvement =
                ResumeImprovementDto.builder()

                        .missingSkills(
                                analysis.getMissingKeywords())

                        .projectSuggestions(
                                analysis.getAiProjectSuggestions())

                        .summarySuggestions(
                                analysis.getAiSummarySuggestions())

                        .atsSuggestions(
                                analysis.getAiAtsSuggestions())

                        .build();

        AiAnalysisResponseDto aiAnalysis =
                AiAnalysisResponseDto.builder()

                        .overallScore(
                                analysis.getAiOverallScore())

                        .overallAssessment(
                                analysis.getAiOverallAssessment())

                        .missingSkills(
                                analysis.getAiMissingSkills())

                        .projectSuggestions(
                                analysis.getAiProjectSuggestions())

                        .summarySuggestions(
                                analysis.getAiSummarySuggestions())

                        .atsSuggestions(
                                analysis.getAiAtsSuggestions())

                        .build();

        return AnalysisResponseDto.builder()

                .resumeKeywords(
                        analysis.getResumeKeywords())

                .jobDescriptionKeywords(
                        analysis.getJobDescriptionKeywords())

                .matchedKeywords(
                        analysis.getMatchedKeywords())

                .missingKeywords(
                        analysis.getMissingKeywords())

                .matchPercentage(
                        analysis.getMatchPercentage())

                .report(report)

                .improvement(improvement)

                .aiAnalysis(aiAnalysis)

                .build();
    }
}