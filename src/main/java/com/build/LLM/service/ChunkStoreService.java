package com.build.LLM.service;

import com.build.LLM.models.Chunk;
import com.build.LLM.repository.VectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChunkStoreService {
    private final VectorRepository vectorRepository;
    public void saveChunks(List<Chunk> chunks) {
        vectorRepository.saveChunk(chunks);
    }
}
