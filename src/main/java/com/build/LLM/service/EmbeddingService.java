package com.build.LLM.service;

import com.google.genai.Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmbeddingService {
    private final Client client;


    public List<Float> createEmbedding(String content){
        var response = client.models.embedContent("gemini-embedding-001",content,null);

        return response.embeddings().get().getFirst().values().orElseThrow(RuntimeException::new);
    }
}
