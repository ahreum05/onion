package com.example.onion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onion.entity.Boardinfo;
import com.example.onion.service.BoardService;

import org.springframework.ui.Model;

@Controller
public class SearchController {

    private final BoardService boardService;

    @Autowired
    public SearchController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        // 검색 결과를 가져오는 서비스 메서드 호출
        List<Boardinfo> searchResults = boardService.searchPosts(query);
        model.addAttribute("results", searchResults);
        return "boardInfo/searchResult"; // 검색 결과를 보여줄 페이지 (searchResult.html)
    }
}

