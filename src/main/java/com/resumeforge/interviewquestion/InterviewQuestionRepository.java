package com.resumeforge.interviewquestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewQuestionRepository
        extends JpaRepository<InterviewQuestion, Long> {

    /**
     * Latest Interview Questions
     */
    Optional<InterviewQuestion> findFirstByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    /**
     * Interview Question History
     */
    List<InterviewQuestion> findAllByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    /**
     * Find by Id of current user
     */
    Optional<InterviewQuestion> findByIdAndUserId(
            Long id,
            Long userId
    );

    /**
     * History for a Resume
     */
    List<InterviewQuestion> findByResumeIdAndUserIdOrderByCreatedAtDesc(
            Long resumeId,
            Long userId
    );
}