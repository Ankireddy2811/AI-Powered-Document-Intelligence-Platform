package com.build.LLM.utils;

import com.build.LLM.models.Chunk;
import com.build.LLM.repository.VectorRepository;
import com.build.LLM.service.EmbeddingService;
import com.pgvector.PGvector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Getter
@Component
@AllArgsConstructor
@Slf4j
public class ChunkCreateUtil {


    private final EmbeddingService embeddingService;
    private final ExecutorService executorService;


    public List<Chunk> createChunkForText(List<String> textList, String fileName) {
        List<CompletableFuture<Chunk>> futures =
                textList.stream().map(text -> CompletableFuture.supplyAsync(() -> {
                    log.info(
                            "Thread: {}",
                            Thread.currentThread().getName()
                    );
                List<Float> embedding = embeddingService.createEmbedding(text);

                float[] embeddingArray = new float[embedding.size()];

                for (int i = 0; i < embedding.size(); i++) {
                    embeddingArray[i] = embedding.get(i);
                }
                Chunk chunk = new Chunk();
                chunk.setContent(text);
                chunk.setEmbedding(new PGvector(embeddingArray));
                chunk.setDocumentName(fileName);
                chunk.setUpdatedAt(LocalDateTime.now());
                return chunk;
        },executorService)).toList();
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();

    }

    //future->future.join()) //wait untill future task completes and returns val;

}

