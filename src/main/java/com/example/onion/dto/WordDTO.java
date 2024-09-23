package com.example.onion.dto;

import com.example.onion.entity.Word;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {
    private Long wordId;
    private String word;
    
    public Word toEntity() {
        return new Word(wordId, word); // 엔티티로 변환
    }
}
