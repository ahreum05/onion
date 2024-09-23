package com.example.onion.service;

import com.example.onion.dto.WordDTO;
import com.example.onion.entity.Word;
import com.example.onion.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    public List<WordDTO> getAllWords() {
        return wordRepository.findAll().stream()
                .map(Word::toDTO)
                .collect(Collectors.toList());
    }

    public void addWord(WordDTO wordDTO) {
        Word word = wordDTO.toEntity(); // DTO -> 엔티티 변환
        wordRepository.save(word);
    }
}
