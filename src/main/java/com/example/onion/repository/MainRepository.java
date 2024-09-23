package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Boardinfo;

public interface MainRepository extends JpaRepository<Boardinfo, Integer> {
	/*@Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM Boardinfo ORDER BY bseq DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Boardinfo> findByStartnumAndEndnum(@Param("startNum") int startNum, @Param("endNum") int endNum);*/

	/*@Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM Boardinfo ORDER BY blogtime DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Boardinfo> findByLogtime(@Param("startNum") int startNum, @Param("endNum") int endNum);*/

	@Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM Boardinfo ORDER BY bhit DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Boardinfo> findByBhit(@Param("startNum") int startNum, @Param("endNum") int endNum);

	/*@Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM Boardinfo ORDER BY blike DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Boardinfo> findByBlike(@Param("startNum") int startNum, @Param("endNum") int endNum);*/

	// 찜수
	@Query(value = "SELECT * FROM " + "(SELECT rownum rn, tt.* FROM "
			+ "(SELECT * FROM Boardinfo ORDER BY Bjjim DESC) tt)"
			+ " WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Boardinfo> findByBjjim(@Param("startNum") int startNum, @Param("endNum") int endNum);

}
