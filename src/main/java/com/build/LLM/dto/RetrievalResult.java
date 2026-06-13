package com.build.LLM.dto;

import java.util.List;

public record RetrievalResult(
    String context,
    List<SourceDto>sources
){}
