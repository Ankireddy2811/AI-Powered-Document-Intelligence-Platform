package com.build.LLM.service;

import com.build.LLM.models.Chunk;
import com.build.LLM.utils.ChunkCreateUtil;
import com.build.LLM.utils.TextSplitUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final PdfService pdfService;
    private final TextSplitUtil textSplitUtil;
    private final ChunkStoreService chunkStoreService;
    private final ChunkCreateUtil chunkCreateUtil;

    @Async
    public void processDocumentAsync(byte[] bytes, String fileName) {
        try {
            //extract the text from pdf
            String text = pdfService.extractTextFromBytes(bytes);

            //split the text into parts
            List<String> textList = textSplitUtil.splitText(text, 400, 100);

            //create the chunk for each text
            List<Chunk> chunks = chunkCreateUtil.createChunkForText(textList, fileName);

            //store the chunks in db
            chunkStoreService.saveChunks(chunks);
            log.info("Successfully finished processing chunks for file: {}", fileName);
        } catch (Exception e) {
            log.error("Failed to process document in background: {}", fileName, e);
        }
    }
}
