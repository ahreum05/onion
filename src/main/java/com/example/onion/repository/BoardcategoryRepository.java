package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Boardcategory;

public interface BoardcategoryRepository extends JpaRepository<Boardcategory, Integer> {

	@Query(value="select * from Boardcategory",nativeQuery = true)
	List<Boardcategory> findAllList();
}
