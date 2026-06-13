package com.build.LLM.repository;

import com.build.LLM.models.Chunk;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class VectorRepository {
    private final JdbcTemplate jdbcTemplate;

    public VectorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveChunk(List<Chunk> chunks) {
        String sql = """
               INSERT INTO document_chunks (content, embedding, document_name, updated_at)
               VALUES (?, CAST(? AS vector), ?, ?)
               """;

        // Pass the entire list. Spring chunks it into batches of 50 automatically.
        jdbcTemplate.batchUpdate(sql, chunks, 50, (ps, chunk) -> {
            ps.setString(1, chunk.getContent());
            ps.setString(2, chunk.getEmbedding().toString());
            ps.setString(3, chunk.getDocumentName());
            ps.setTimestamp(4, Timestamp.valueOf(chunk.getUpdatedAt()));
        });
    }
}
