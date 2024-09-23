package com.example.onion.dto;

import com.example.onion.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerDTO {
    private Long id;
    private String imageUrl;
    private String title;
    private String description;
    private Boolean isActive;
    private String createdAt;  // String으로 처리 (자동 생성된 값 사용)
    private String updatedAt;
    
    // DTO를 엔티티로 변환하는 메서드
    public Banner toEntity() {
        return Banner.builder()
                .id(id)  // 기존 id를 전달
                .imageUrl(imageUrl)
                .title(title)
                .description(description)
                .isActive(isActive)
                .build();  // createdAt과 updatedAt은 엔티티에서 자동 처리됨
    }
}
