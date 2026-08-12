package com.resumeforge.analysis;

import com.resumeforge.auth.user.User;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.resume.Resume;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Owner of this analysis
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Resume used for analysis
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    /**
     * Job Description used for analysis
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_description_id", nullable = false)
    private JobDescription jobDescription;

    /**
     * ATS Match Percentage
     */
    @Column(nullable = false)
    private Double matchPercentage;

    // ======================================
    // Resume Keywords
    // ======================================

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_resume_keywords",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "keyword")
    @Builder.Default
    private List<String> resumeKeywords = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_job_keywords",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "keyword")
    @Builder.Default
    private List<String> jobDescriptionKeywords = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_matched_keywords",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "keyword")
    @Builder.Default
    private List<String> matchedKeywords = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_missing_keywords",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "keyword")
    @Builder.Default
    private List<String> missingKeywords = new ArrayList<>();

    // ======================================
    // ATS Report
    // ======================================

    @Column(columnDefinition = "TEXT")
    private String overallAssessment;

    @Column(length = 100)
    private String scoreCategory;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_strengths",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "strength")
    @Builder.Default
    private List<String> strengths = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_improvements",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "improvement")
    @Builder.Default
    private List<String> improvements = new ArrayList<>();
    @Column(columnDefinition = "TEXT")
    private String recommendation;

    // ======================================
    // AI Analysis
    // ======================================

    private Integer aiOverallScore;

    @Column(columnDefinition = "TEXT")
    private String aiOverallAssessment;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_ai_missing_skills",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "skill")
    @Builder.Default
    private List<String> aiMissingSkills = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_ai_project_suggestions",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "suggestion")
    @Builder.Default
    private List<String> aiProjectSuggestions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_ai_summary_suggestions",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "suggestion")
    @Builder.Default
    private List<String> aiSummarySuggestions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "analysis_ai_ats_suggestions",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "suggestion")
    @Builder.Default
    private List<String> aiAtsSuggestions = new ArrayList<>();

    /**
     * Analysis Creation Time
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}