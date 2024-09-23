package com.example.onion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

import com.example.onion.entity.Manager;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDTO {
    
    private String mid;
    private String mpwd;
    private LocalDateTime mlogtime;
    private String role;

    // DTO -> Entity 변환 메서드
    public Manager toEntity() {
        return new Manager(mid, mpwd, mlogtime, role);
    }
}
