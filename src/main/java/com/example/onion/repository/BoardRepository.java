package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onion.entity.Boardinfo;

public interface BoardRepository extends JpaRepository<Boardinfo, Integer> {
    List<Boardinfo> findByBsubContainingIgnoreCase(String query);
}


