package com.resumeforge.analysis;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiResumeAnalysisService {

    private final ChatClient chatClient;

    public AiResumeAnalysisService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateSuggestions(String resumeText, String jobDescription) {

        String prompt = """
                You are an expert ATS resume reviewer.

                Analyze the following resume against the job description.

                Resume:
                %s

                Job Description:
                %s

                Provide:
                1. Overall ATS analysis
                2. Missing skills
                3. Resume improvement suggestions
                4. Professional summary improvements

                Keep the response concise and well structured.
                """.formatted(resumeText, jobDescription);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}