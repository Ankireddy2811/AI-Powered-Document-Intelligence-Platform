package com.build.LLM.dto;

public record ChatRequest(
        String sessionId,
        String text,
        String personality,
        Float temperature, // Use object Wrapper so it safely handles optional payloads without throwing crashes
        Integer maxTokens
) {
}

