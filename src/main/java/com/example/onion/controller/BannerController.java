package com.example.onion.controller;

import com.example.onion.dto.BannerDTO;
import com.example.onion.dto.Manager_boardDTO;
import com.example.onion.service.BannerService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {
	@Value("${project.upload.path}")
	private String uploadpath;
	
	
	@Autowired
	private BannerService bannerService;

	// 모든 배너 조회 (API 방식)
	@GetMapping
	public ResponseEntity<List<BannerDTO>> getAllBanners() {
		List<BannerDTO> banners = bannerService.getAllBanners();
		return ResponseEntity.ok(banners);
	}

	// ID로 배너 조회 (API 방식)
	@GetMapping("/{id}")
	public ResponseEntity<BannerDTO> getBannerById(@PathVariable Long id) {
		BannerDTO bannerDTO = bannerService.getBannerById(id);
		return ResponseEntity.ok(bannerDTO);
	}

	/*
	 * // 배너 추가 (API 방식)
	 * 
	 * @PostMapping public ResponseEntity<BannerDTO> addBanner(@RequestBody
	 * BannerDTO bannerDTO) { BannerDTO newBanner =
	 * bannerService.addBanner(bannerDTO); return ResponseEntity.ok(newBanner); }
	 */

	// 배너 추가 (API 방식)
	@PostMapping
	public ResponseEntity<BannerDTO> addBanner(BannerDTO dto, Model model,
			@RequestParam("img") MultipartFile uploadFile, HttpServletRequest request) {
		
		String fileName =  uploadFile.getOriginalFilename();
		dto.setImageUrl(fileName);

		if (!fileName.equals("")) {
			File file = new File(uploadpath, fileName);
			try {
				uploadFile.transferTo(file);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		BannerDTO newBanner = bannerService.addBanner(dto);
		return ResponseEntity.ok(newBanner);
	}

	

	// 배너 수정 (API 방식)
	@PutMapping("/{id}")
	public ResponseEntity<BannerDTO> updateBanner(@PathVariable Long id, @RequestBody BannerDTO bannerDTO) {
		BannerDTO updatedBanner = bannerService.updateBanner(id, bannerDTO);
		return ResponseEntity.ok(updatedBanner);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBanner(@PathVariable("id") Long id) {
		bannerService.deleteBanner(id);
		return ResponseEntity.noContent().build();
	}
}
