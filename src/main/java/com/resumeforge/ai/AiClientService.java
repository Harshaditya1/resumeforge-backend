package com.resumeforge.ai;

import com.resumeforge.analysis.dto.AiAnalysisResponseDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiClientService {

    private final ChatClient chatClient;

    public AiClientService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public AiAnalysisResponseDto ask(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(AiAnalysisResponseDto.class);
    }
}