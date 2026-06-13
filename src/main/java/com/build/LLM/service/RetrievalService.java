package com.build.LLM.service;

import com.build.LLM.dto.RetrievalResult;
import com.build.LLM.dto.SourceDto;
import com.build.LLM.models.Chunk;
import com.build.LLM.repository.ChunkRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RetrievalService {

    private final EmbeddingService embeddingService;
    private final ChunkRepository chunkRepository;


    public RetrievalResult retrieveRelevantChunk(
            String question){

        String questionEmbedding = embeddingService.createEmbedding(question).toString();
        List<Chunk>similarChunks = chunkRepository.findTop3SimilarChunks(questionEmbedding);
        log.info("===== RETRIEVED CHUNKS =====");

        String context = similarChunks.stream().map(Chunk::getContent).reduce("",(a, b)->a+"\n"+b);
        List<SourceDto> sources = similarChunks.stream().map(chunk -> new SourceDto(chunk.getDocumentName(),chunk.getContent())).toList();
        return new RetrievalResult(context,sources);
    }
}

