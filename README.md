# AI-Powered Document Intelligence Platform (RAG)

An AI-powered Document Question Answering platform built using Java, Spring Boot, PostgreSQL, pgvector, and Google Gemini.

The application enables users to upload PDF documents and ask natural language questions. It leverages Retrieval-Augmented Generation (RAG) to retrieve relevant document content and generate accurate, context-aware answers with source attribution.

---

## Features

- PDF Upload & Processing
- Intelligent Text Chunking with Overlap
- Embedding Generation using Google Gemini
- Semantic Search using PostgreSQL pgvector
- Retrieval-Augmented Generation (RAG)
- Source Attribution for Explainable AI
- JDBC Batch Inserts for High-Performance Ingestion
- Multiple Prompt Strategies
  - Friendly Mode
  - Academic Mode
  - Document QA Mode
- Metadata-Based Document Storage
- Scalable Backend Architecture

---

## Architecture

```text
User Uploads PDF
        |
        v
PDF Extraction (Apache PDFBox)
        |
        v
Chunking + Overlap
        |
        v
Gemini Embedding Model
        |
        v
PostgreSQL + pgvector
        |
        v
Semantic Similarity Search
        |
        v
Relevant Context Retrieval
        |
        v
Gemini LLM
        |
        v
Answer + Sources
```

---

## Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring AI
- Spring Data JPA
- JDBC Template

### AI & NLP

- Google Gemini
- Embeddings API
- Retrieval-Augmented Generation (RAG)

### Database

- PostgreSQL
- pgvector

### Document Processing

- Apache PDFBox

### Build Tool

- Maven

---

## Project Structure

```text
src/main/java
│
├── controller
│   ├── ChatController
│   └── DocumentController
│
├── service
│   ├── ChatService
│   ├── DocumentService
│   ├── EmbeddingService
│   └── RetrievalService
│
├── repository
│   └── ChunkRepository
│
├── model
│   └── Chunk
│
├── dto
│
└── config
```

---

## Database Schema

### chunks

| Column | Type |
|----------|----------|
| id | BIGINT |
| content | TEXT |
| embedding | VECTOR |
| document_name | VARCHAR |
| created_at | TIMESTAMP |
| updated_at | TIMESTAMP |

---

## API Endpoints

### Upload Document

```http
POST /api/documents/upload
```

**Form Data**

```text
file=sample.pdf
```

---

### Ask Question

```http
POST /api/chat
```

### Request

```json
{
  "question": "What is my PF number?"
}
```

### Response

```json
{
  "answer": "The document contains a PF Account Number field...",
  "sources": [
    {
      "documentName": "employee_form.pdf",
      "chunkText": "PF Account Number..."
    }
  ]
}
```

---

## How RAG Works

### Step 1: Upload PDF

The user uploads a PDF document.

### Step 2: Extract Text

Apache PDFBox extracts textual content from the document.

### Step 3: Chunking

The document is split into smaller overlapping chunks.

### Step 4: Generate Embeddings

Each chunk is converted into vector embeddings using Google Gemini.

### Step 5: Store in PostgreSQL

Chunks and embeddings are stored in PostgreSQL with pgvector support.

### Step 6: User Question

The user submits a natural language question.

### Step 7: Generate Query Embedding

The question is converted into an embedding vector.

### Step 8: Semantic Retrieval

Top matching chunks are retrieved using vector similarity search.

### Step 9: Context Construction

Retrieved chunks are combined into a context window.

### Step 10: Answer Generation

Gemini generates a grounded answer using retrieved context and returns source references.

---

## Sample Vector Search Query

```sql
SELECT *
FROM chunks
ORDER BY embedding <=> CAST(? AS vector)
LIMIT 3;
```

---

## Performance Optimizations

- JDBC Batch Inserts
- Chunk Overlap Strategy
- Metadata-Based Storage
- Top-K Retrieval
- Vector Similarity Search
- Context Compression

---

## Author

**Ankaiah Puttam**

Backend Developer | Java | Spring Boot | PostgreSQL | AI Engineering
