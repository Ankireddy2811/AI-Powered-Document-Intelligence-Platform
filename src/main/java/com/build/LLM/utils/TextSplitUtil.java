package com.build.LLM.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextSplitUtil {

    public List<String> splitText(String text, int chunkSize,int overlap){
        List<String> textList =
                new ArrayList<>();

        int start = 0, len = text.length();
        while(start < len){
            int end = Math.min(start + chunkSize,len);

            textList.add(text.substring(start,end));
            start = start + (chunkSize-overlap);
        }
        //0 to 400
        //300 to 700
        //600 to 1000

        return textList;
    }
}

