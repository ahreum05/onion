package com.example.onion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onion.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
	
}
