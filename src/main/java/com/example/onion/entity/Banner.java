package com.example.onion.entity;

import java.sql.Timestamp;

import com.example.onion.dto.BannerDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "banner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banner_seq")
    @SequenceGenerator(name = "banner_seq", sequenceName = "BANNER_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    
    // DTO로 변환하는 메서드
    public BannerDTO toDTO() {
        return new BannerDTO(
            id,
            imageUrl,
            title,
            description,
            isActive,
            createdAt != null ? createdAt.toString() : null,
            updatedAt != null ? updatedAt.toString() : null
        );
    }
}


