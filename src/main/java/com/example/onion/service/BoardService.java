package com.example.onion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.entity.Boardinfo;
import com.example.onion.repository.BoardRepository;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 제목에 검색어가 포함된 게시글을 반환하는 메서드
    public List<Boardinfo> searchPosts(String query) {
        return boardRepository.findByBsubContainingIgnoreCase(query);
    }
}


