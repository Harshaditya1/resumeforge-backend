package com.resumeforge.generatedresume;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratedResumeRepository extends JpaRepository<GeneratedResume, Long> {

    List<GeneratedResume> findByResumeIdOrderByCreatedAtDesc(Long resumeId);

    List<GeneratedResume> findByJobDescriptionIdOrderByCreatedAtDesc(Long jobDescriptionId);
}