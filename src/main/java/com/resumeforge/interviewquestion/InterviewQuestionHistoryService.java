package com.resumeforge.interviewquestion;

import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.exception.ResourceNotFoundException;
import com.resumeforge.interviewquestion.dto.InterviewQuestionDetailsResponseDto;
import com.resumeforge.interviewquestion.dto.InterviewQuestionHistoryResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewQuestionHistoryService {

    private final InterviewQuestionRepository interviewQuestionRepository;
    private final CurrentUserService currentUserService;

    public InterviewQuestionHistoryService(
            InterviewQuestionRepository interviewQuestionRepository,
            CurrentUserService currentUserService
    ) {
        this.interviewQuestionRepository = interviewQuestionRepository;
        this.currentUserService = currentUserService;
    }

    /**
     * Save Interview Questions
     */
    public InterviewQuestion save(
            InterviewQuestion interviewQuestion
    ) {
        return interviewQuestionRepository.save(interviewQuestion);
    }

    /**
     * Latest Interview Questions
     */
    public InterviewQuestionDetailsResponseDto getLatest() {

        Long userId = currentUserService.getCurrentUser().getId();

        InterviewQuestion interviewQuestion =
                interviewQuestionRepository
                        .findFirstByUserIdOrderByCreatedAtDesc(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No interview questions found."
                                ));

        return mapToDetailsDto(interviewQuestion);
    }

    /**
     * Interview Question History
     */
    public List<InterviewQuestionHistoryResponseDto> getHistory() {

        Long userId = currentUserService.getCurrentUser().getId();

        return interviewQuestionRepository
                .findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToHistoryDto)
                .toList();
    }

    /**
     * Get Interview Questions by Id
     */
    public InterviewQuestionDetailsResponseDto getById(Long id) {

        Long userId = currentUserService.getCurrentUser().getId();

        InterviewQuestion interviewQuestion =
                interviewQuestionRepository
                        .findByIdAndUserId(id, userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview questions not found."
                                ));

        return mapToDetailsDto(interviewQuestion);
    }

    /**
     * Delete Interview Questions
     */
    public void delete(Long id) {

        Long userId = currentUserService.getCurrentUser().getId();

        InterviewQuestion interviewQuestion =
                interviewQuestionRepository
                        .findByIdAndUserId(id, userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Interview questions not found."
                                ));

        interviewQuestionRepository.delete(interviewQuestion);
    }

    private InterviewQuestionHistoryResponseDto mapToHistoryDto(
            InterviewQuestion interviewQuestion
    ) {

        return InterviewQuestionHistoryResponseDto.builder()
                .id(interviewQuestion.getId())
                .resumeId(interviewQuestion.getResume().getId())
                .jobDescriptionId(interviewQuestion.getJobDescription().getId())
                .createdAt(interviewQuestion.getCreatedAt())
                .build();
    }

    private InterviewQuestionDetailsResponseDto mapToDetailsDto(
            InterviewQuestion interviewQuestion
    ) {

        return InterviewQuestionDetailsResponseDto.builder()
                .id(interviewQuestion.getId())
                .resumeId(interviewQuestion.getResume().getId())
                .jobDescriptionId(interviewQuestion.getJobDescription().getId())
                .questions(interviewQuestion.getQuestions())
                .createdAt(interviewQuestion.getCreatedAt())
                .build();
    }
}