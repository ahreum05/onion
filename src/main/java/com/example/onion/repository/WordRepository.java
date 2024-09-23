package com.example.onion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onion.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
    // 금지어 관련 메서드를 정의할 수 있음
}

