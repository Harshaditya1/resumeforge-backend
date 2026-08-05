package com.resumeforge.jobdescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription, Long> {

    Optional<JobDescription> findByIdAndUserId(Long id, Long userId);

    List<JobDescription> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}