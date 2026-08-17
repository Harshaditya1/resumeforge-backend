package com.resumeforge.ai;

import com.resumeforge.ai.prompt.InterviewQuestionPromptBuilder;
import com.resumeforge.analysis.dto.AIInterviewQuestionResponseDto;
import com.resumeforge.auth.CurrentUserService;
import com.resumeforge.auth.user.User;
import com.resumeforge.interviewquestion.InterviewQuestion;
import com.resumeforge.interviewquestion.InterviewQuestionHistoryService;
import com.resumeforge.jobdescription.JobDescription;
import com.resumeforge.resume.Resume;
import org.springframework.stereotype.Service;

@Service
public class InterviewQuestionService {

    private final CurrentResumeContextService currentResumeContextService;
    private final InterviewQuestionPromptBuilder promptBuilder;
    private final AiClientService aiClientService;
    private final InterviewQuestionHistoryService interviewQuestionHistoryService;
    private final CurrentUserService currentUserService;

    public InterviewQuestionService(
            CurrentResumeContextService currentResumeContextService,
            InterviewQuestionPromptBuilder promptBuilder,
            AiClientService aiClientService,
            InterviewQuestionHistoryService interviewQuestionHistoryService,
            CurrentUserService currentUserService
    ) {
        this.currentResumeContextService = currentResumeContextService;
        this.promptBuilder = promptBuilder;
        this.aiClientService = aiClientService;
        this.interviewQuestionHistoryService = interviewQuestionHistoryService;
        this.currentUserService = currentUserService;
    }

    public AIInterviewQuestionResponseDto generateInterviewQuestions() {

        User currentUser = currentUserService.getCurrentUser();

        Resume resume = currentResumeContextService.getLatestResume();

        JobDescription jobDescription =
                currentResumeContextService.getLatestJobDescription();

        String prompt = promptBuilder.buildPrompt(
                resume.getExtractedText(),
                jobDescription.getContent()
        );

        AIInterviewQuestionResponseDto response =
                aiClientService.ask(
                        prompt,
                        AIInterviewQuestionResponseDto.class
                );

        StringBuilder questions = new StringBuilder();

        if (response.getTechnicalQuestions() != null
                && !response.getTechnicalQuestions().isEmpty()) {

            questions.append("========== TECHNICAL QUESTIONS ==========\n\n");

            response.getTechnicalQuestions()
                    .forEach(question ->
                            questions.append(question).append("\n"));
        }

        if (response.getHrQuestions() != null
                && !response.getHrQuestions().isEmpty()) {

            questions.append("\n========== HR QUESTIONS ==========\n\n");

            response.getHrQuestions()
                    .forEach(question ->
                            questions.append(question).append("\n"));
        }

        if (response.getBehavioralQuestions() != null
                && !response.getBehavioralQuestions().isEmpty()) {

            questions.append("\n========== BEHAVIORAL QUESTIONS ==========\n\n");

            response.getBehavioralQuestions()
                    .forEach(question ->
                            questions.append(question).append("\n"));
        }

        InterviewQuestion interviewQuestion =
                InterviewQuestion.builder()
                        .user(currentUser)
                        .resume(resume)
                        .jobDescription(jobDescription)
                        .questions(questions.toString())
                        .build();

        interviewQuestionHistoryService.save(interviewQuestion);

        return response;
    }
}