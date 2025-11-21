package com.fiap.globalsolution.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getRecommendation(String topic) {
        String prompt = "Recomende 3 tópicos de estudo avançado para um funcionário interessado em: " + topic;
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    public String generateCourseDescription(String courseName) {
        String prompt = "Gere uma descrição curta e atrativa para um curso corporativo chamado: " + courseName;
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
