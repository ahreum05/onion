package com.example.onion.service;

import com.example.onion.dto.BannerDTO;
import com.example.onion.entity.Banner;
import com.example.onion.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    // 배너 리스트 가져오기 (DTO로 변환)
    public List<BannerDTO> getAllBanners() {
        return bannerRepository.findAll()
                .stream()
                .map(Banner::toDTO)
                .collect(Collectors.toList());
    }

    // ID로 배너 조회
    public BannerDTO getBannerById(Long id) {
        return bannerRepository.findById(id)
                .map(Banner::toDTO)
                .orElseThrow(() -> new RuntimeException("배너를 찾을 수 없습니다."));
    }

    // 배너 추가
    public BannerDTO addBanner(BannerDTO bannerDTO) {
        Banner banner = bannerDTO.toEntity();
        return bannerRepository.save(banner).toDTO();
    }

    // 배너 수정
    public BannerDTO updateBanner(Long id, BannerDTO bannerDTO) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("배너를 찾을 수 없습니다."));
        
        banner.setImageUrl(bannerDTO.getImageUrl());
        banner.setTitle(bannerDTO.getTitle());
        banner.setDescription(bannerDTO.getDescription());
        banner.setIsActive(bannerDTO.getIsActive());

        return bannerRepository.save(banner).toDTO();
    }

    // 배너 삭제
    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }
}
