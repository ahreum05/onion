package com.example.onion.entity;

import com.example.onion.dto.WordDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "word")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    @Column(nullable = false)
    private String word;
    
    // DTO 변환 메서드
    public WordDTO toDTO() {
        return new WordDTO(wordId, word);
    }
}
