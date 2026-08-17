package com.resumeforge.tailoring;

import com.resumeforge.auth.user.User;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.resume.Resume;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tailored_resumes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TailoredResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Owner of this tailored resume
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Source Resume
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    /**
     * Source Job Description
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_description_id", nullable = false)
    private JobDescription jobDescription;

    /**
     * AI Generated Professional Summary
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String professionalSummary;

    // ======================================
    // Skills
    // ======================================

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tailored_resume_skills",
            joinColumns = @JoinColumn(name = "tailored_resume_id")
    )
    @Column(name = "skill")
    @Builder.Default
    private List<String> skills = new ArrayList<>();

    // ======================================
    // Experience Suggestions
    // ======================================

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tailored_resume_experience",
            joinColumns = @JoinColumn(name = "tailored_resume_id")
    )
    @Column(name = "suggestion")
    @Builder.Default
    private List<String> experienceSuggestions =
            new ArrayList<>();

    // ======================================
    // Project Suggestions
    // ======================================

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tailored_resume_projects",
            joinColumns = @JoinColumn(name = "tailored_resume_id")
    )
    @Column(name = "suggestion")
    @Builder.Default
    private List<String> projectSuggestions =
            new ArrayList<>();
    // ======================================
    // Missing Keywords
    // ======================================

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tailored_resume_missing_keywords",
            joinColumns = @JoinColumn(name = "tailored_resume_id")
    )
    @Column(name = "keyword")
    @Builder.Default
    private List<String> missingKeywords =
            new ArrayList<>();

    // ======================================
    // Overall Suggestions
    // ======================================

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tailored_resume_overall_suggestions",
            joinColumns = @JoinColumn(name = "tailored_resume_id")
    )
    @Column(name = "suggestion")
    @Builder.Default
    private List<String> overallSuggestions =
            new ArrayList<>();

    // ======================================
    // Resume Version Information
    // ======================================

    /**
     * Version number of the tailored resume.
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer versionNumber = 1;

    /**
     * Whether the user approved this tailored resume.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean approved = false;

    /**
     * Whether this resume has been downloaded.
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean downloaded = false;

    /**
     * Generated resume content (future use).
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String generatedResume;

    /**
     * Generated PDF/DOCX path (future use).
     */
    private String downloadPath;

    /**
     * Creation time.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}