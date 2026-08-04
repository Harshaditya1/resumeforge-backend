package com.resumeforge.tailoring;

import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.resume.Resume;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_description_id", nullable = false)
    private JobDescription jobDescription;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String professionalSummary;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String skills;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String experienceSuggestions;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String projectSuggestions;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String missingKeywords;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String overallSuggestions;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}