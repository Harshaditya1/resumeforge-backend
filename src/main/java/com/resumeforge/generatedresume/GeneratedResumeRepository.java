package com.resumeforge.generatedresume;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GeneratedResumeRepository extends JpaRepository<GeneratedResume, Long> {

    List<GeneratedResume> findByResumeIdAndUserIdOrderByCreatedAtDesc(
            Long resumeId,
            Long userId
    );

    List<GeneratedResume> findByJobDescriptionIdAndUserIdOrderByCreatedAtDesc(
            Long jobDescriptionId,
            Long userId
    );

    Optional<GeneratedResume> findByIdAndUserId(
            Long id,
            Long userId
    );

    List<GeneratedResume> findAllByUserIdOrderByCreatedAtDesc(
            Long userId
    );
}