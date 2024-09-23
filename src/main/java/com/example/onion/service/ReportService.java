package com.example.onion.service;

import com.example.onion.dto.ReportDTO;
import com.example.onion.entity.Report;
import com.example.onion.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll().stream()
                .map(Report::toDTO)
                .collect(Collectors.toList());
    }

    public void addReport(ReportDTO reportDTO) {
        Report report = reportDTO.toEntity(); // DTO -> 엔티티 변환
        reportRepository.save(report);
    }
}
