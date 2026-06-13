package com.build.LLM.repository;

import com.build.LLM.models.Chunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChunkRepository extends JpaRepository<Chunk,Long> {


    @Query(value = """
       SELECT *
       FROM document_chunks
       ORDER BY embedding <=> CAST(:queryVector AS vector)
       LIMIT 3
        """,
            nativeQuery = true)
    List<Chunk> findTop3SimilarChunks(@Param("queryVector") String text);

}
