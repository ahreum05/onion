package com.example.onion.dto;

import com.example.onion.entity.Report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Long reportId;
    private String userId;
    private String reportReason;
    private int reportCount;
    
    public Report toEntity() {
        return new Report(reportId, userId, reportReason, reportCount); // 엔티티로 변환
    }
}
