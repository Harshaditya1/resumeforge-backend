package com.resumeforge.analysis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalysisRepository
        extends JpaRepository<Analysis, Long> {

    Optional<Analysis> findFirstByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    List<Analysis> findAllByUserIdOrderByCreatedAtDesc(
            Long userId
    );
}