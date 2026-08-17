package com.resumeforge.interviewquestion;

import com.resumeforge.ai.InterviewQuestionService;
import com.resumeforge.analysis.dto.AIInterviewQuestionResponseDto;
import com.resumeforge.interviewquestion.dto.InterviewQuestionDetailsResponseDto;
import com.resumeforge.interviewquestion.dto.InterviewQuestionHistoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview-questions")
@CrossOrigin(origins = "http://localhost:5173")
public class InterviewQuestionController {

    private final InterviewQuestionService interviewQuestionService;
    private final InterviewQuestionHistoryService interviewQuestionHistoryService;

    public InterviewQuestionController(
            InterviewQuestionService interviewQuestionService,
            InterviewQuestionHistoryService interviewQuestionHistoryService
    ) {
        this.interviewQuestionService = interviewQuestionService;
        this.interviewQuestionHistoryService = interviewQuestionHistoryService;
    }

    /**
     * Generate Interview Questions
     */
    @Operation(summary = "Generate interview questions")
    @PostMapping
    public AIInterviewQuestionResponseDto generateInterviewQuestions() {

        return interviewQuestionService.generateInterviewQuestions();
    }

    /**
     * Latest Interview Questions
     */
    @Operation(summary = "Get latest interview questions")
    @GetMapping("/latest")
    public InterviewQuestionDetailsResponseDto getLatest() {

        return interviewQuestionHistoryService.getLatest();
    }

    /**
     * Interview Question History
     */
    @Operation(summary = "Get interview question history")
    @GetMapping("/history")
    public List<InterviewQuestionHistoryResponseDto> getHistory() {

        return interviewQuestionHistoryService.getHistory();
    }

    /**
     * Interview Question Details
     */
    @Operation(summary = "Get interview question details")
    @GetMapping("/{id}")
    public InterviewQuestionDetailsResponseDto getById(
            @PathVariable Long id
    ) {

        return interviewQuestionHistoryService.getById(id);
    }

    /**
     * Delete Interview Questions
     */
    @Operation(summary = "Delete interview questions")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        interviewQuestionHistoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}