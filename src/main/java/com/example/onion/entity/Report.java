package com.example.onion.entity;

import com.example.onion.dto.ReportDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String reportReason;

    @Column(nullable = false)
    private int reportCount;
    
    // DTO 변환 메서드
    public ReportDTO toDTO() {
        return new ReportDTO(reportId, userId, reportReason, reportCount);
    }
}
