package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Storecategory;

public interface StoreCategoryRepository extends JpaRepository<Storecategory, Integer> {

	

	
	@Query(value = "select * from Storecategory", nativeQuery = true)
	List<Storecategory> findByAllStoreCategory();

	@Query(value = "select * from Storecategory where SCname =:SCname", nativeQuery = true)
	List<Storecategory> findBySCname(@Param("SCname") String SCname);



}
