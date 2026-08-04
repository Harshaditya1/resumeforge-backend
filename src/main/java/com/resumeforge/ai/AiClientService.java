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

    public <T> T ask(String prompt, Class<T> responseType) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(responseType);
    }
}