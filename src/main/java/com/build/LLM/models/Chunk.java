package com.build.LLM.models;

import com.pgvector.PGvector;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_chunks")
@Getter
@Setter
public class Chunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Transient
    private PGvector embedding;

    private String documentName;

    private LocalDateTime updatedAt;
}