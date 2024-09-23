package com.example.onion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onion.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // 특정 사용자의 신고 횟수를 조회하는 메서드
    int countByUserId(String userId);
}

