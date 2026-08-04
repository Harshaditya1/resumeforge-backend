package com.resumeforge.tailoring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TailoredResumeRepository extends JpaRepository<TailoredResume, Long> {

    List<TailoredResume> findByResumeIdOrderByCreatedAtDesc(Long resumeId);

    List<TailoredResume> findByJobDescriptionIdOrderByCreatedAtDesc(Long jobDescriptionId);
}