package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Boardinfo;

public interface BoardInfoRepository extends JpaRepository<Boardinfo, Integer> {
	@Query(value = "select * from" + " (select rownum rn, tt.* from "
			+ " (select * from boardInfo order by Bseq desc) tt) "
			+ " where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Boardinfo> findByStartnumAndEndnum(@Param("startNum") int startNum, @Param("endNum") int endNum);

	@Query(value = "SELECT * FROM " + "(SELECT rownum rn, tt.* FROM "
			+ "(SELECT * FROM Boardinfo ORDER BY blogtime DESC) tt)"
			+ " WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Boardinfo> findByLogtime(@Param("startNum") int startNum, @Param("endNum") int endNum);

	@Query(value = "select * from Boardinfo where Bcategory=:Bcategory", nativeQuery = true)
	List<Boardinfo> findByBcategory(@Param("Bcategory") String Bcategory);

	@Query("SELECT COUNT(b) FROM Boardinfo b WHERE b.Bcategory = :tag")
	int countByTag(@Param("tag") String tag);

	// 아이디별 게시글 글 수
	@Query(value = "select count(*) from Boardinfo where Bid=:id", nativeQuery = true)
	Integer boardcount(@Param("id") String id);

	// 아이디별 총 게시글 리스트
	@Query(value = "select * from Boardinfo where Bid=:id", nativeQuery = true)
	List<Boardinfo> findByBid(@Param("id") String id);

	@Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM boardInfo WHERE Bsub LIKE %:search% OR Bcon LIKE %:search% ORDER BY Bseq DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Boardinfo> findByStartnumAndEndnumAndSearch(@Param("startNum") int startNum, @Param("endNum") int endNum,
			@Param("search") String search);

}
