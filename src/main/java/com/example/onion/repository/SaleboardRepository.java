package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.dto.SaleboardDTO;
import com.example.onion.entity.Saleboard;

public interface SaleboardRepository extends JpaRepository<Saleboard, Integer> {
	@Query(value = "select * from Saleboard where SBid=:id", nativeQuery = true)
	List<Saleboard> findBySBIdList(@Param("id") String id);
	
    @Query(value="SELECT COUNT(*) FROM Saleboard WHERE is_available = 1 and SBid=:id",nativeQuery = true)
    Integer findByCount(@Param("id") String id);


}
