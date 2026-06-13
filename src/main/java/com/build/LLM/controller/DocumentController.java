package com.build.LLM.controller;


import com.build.LLM.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@RestController
@RequestMapping("/document")
@AllArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    @PostMapping("/upload")
    private ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();
        documentService.processDocumentAsync(fileBytes,fileName);
        return ResponseEntity.accepted().body("Document upload accepted.");
    }
}
