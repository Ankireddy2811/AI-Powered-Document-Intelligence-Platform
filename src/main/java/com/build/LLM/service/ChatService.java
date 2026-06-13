package com.build.LLM.service;


import com.build.LLM.dto.ChatResponse;
import com.build.LLM.dto.RetrievalResult;
import com.build.LLM.utils.PromptUtil;
import com.google.genai.Chat;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service //creates a bean
@RequiredArgsConstructor
public class ChatService {

    private final Client client;
    private final PromptUtil promptUtil;
    private final RetrievalService retrievalService;

    @Value("${gemini.model}")
    private String modelName;

    private final Map<String, Chat> chatSessions = new ConcurrentHashMap<>();


    public ChatResponse askQuestion(String sessionId, String prompt, String personality, float temperature, int tokens) {

        String systemPrompt = promptUtil.getPersonalityValue(personality);

        GenerateContentConfig generateContentConfig =
                GenerateContentConfig.
                        builder().
                        systemInstruction(Content.builder().parts(Part.builder().text(systemPrompt).build())).
                        temperature(temperature).
                        maxOutputTokens(tokens).
                        build();

//       GenerateContentResponse generateContent = client.models.generateContent( modelName,
//                prompt,
//                generateContentConfig
//                );

        Chat chat = chatSessions.computeIfAbsent(sessionId, id -> client.chats.create(modelName, generateContentConfig));
        RetrievalResult retrievalResult = retrievalService.retrieveRelevantChunk(prompt);
        String response = chat.sendMessage(promptUtil.getFinalPrompt(retrievalResult.context(),prompt), null).text();
        return new ChatResponse(response,retrievalResult.sources());

        //return generateContent.text();
    }

}
