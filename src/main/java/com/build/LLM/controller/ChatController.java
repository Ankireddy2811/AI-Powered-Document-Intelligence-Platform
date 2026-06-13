package com.build.LLM.controller;

import com.build.LLM.dto.ChatRequest;
import com.build.LLM.dto.ChatResponse;
import com.build.LLM.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatResponse askQuestion(@RequestBody ChatRequest request){
        String sessionId = (request.sessionId() != null) ? request.sessionId() : "default-session";
        float temp = (request.temperature() != null) ? request.temperature() : 0.7f;
        int tokens = (request.maxTokens() != null) ? request.maxTokens() : 500;
        String personality = (request.personality() != null) ? request.personality() : "Friendly";
        return chatService.askQuestion(sessionId,request.text(),personality, temp,tokens);
    }
}



