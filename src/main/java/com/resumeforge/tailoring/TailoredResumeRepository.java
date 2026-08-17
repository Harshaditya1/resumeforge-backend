package com.resumeforge.tailoring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TailoredResumeRepository
        extends JpaRepository<TailoredResume, Long> {

    /**
     * Latest tailored resume for a resume.
     */
    Optional<TailoredResume> findFirstByResumeIdOrderByCreatedAtDesc(
            Long resumeId
    );

    /**
     * History for a resume.
     */
    List<TailoredResume> findByResumeIdOrderByCreatedAtDesc(
            Long resumeId
    );

    /**
     * Latest tailored resume for a job description.
     */
    Optional<TailoredResume> findFirstByJobDescriptionIdOrderByCreatedAtDesc(
            Long jobDescriptionId
    );

    /**
     * History for a job description.
     */
    List<TailoredResume> findByJobDescriptionIdOrderByCreatedAtDesc(
            Long jobDescriptionId
    );
}