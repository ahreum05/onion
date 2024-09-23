package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.dto.JobboardDTO;
import com.example.onion.entity.Jobboard;

public interface JobBoardRepository extends JpaRepository<Jobboard, Integer> {

	@Query(value = "SELECT * FROM Jobboard WHERE JBid = :id", nativeQuery = true)
	List<Jobboard> findByAllJobboard(@Param("id") String id);
	
	@Query(value = "SELECT * FROM Jobboard", nativeQuery = true)
	List<Jobboard> findByAllJob();

    @Query("SELECT j FROM Jobboard j JOIN FETCH j.saleuser s WHERE j.JBid = s.Saleid")
    List<Jobboard> findAllJobWithSaleuser();

    @Query("SELECT j FROM Jobboard j JOIN FETCH j.saleuser WHERE j.JBseq = :seq")
    Jobboard findByJobWithSaleuser(@Param("seq") int seq);
}