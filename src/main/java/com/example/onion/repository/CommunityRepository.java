package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Integer> {
    @Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM community ORDER BY cseq DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
    List<Community> findByStartnumAndEndnum(@Param("startNum") int startNum, @Param("endNum") int endNum);
    
    @Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM community WHERE csub LIKE %:search% OR ccon LIKE %:search% ORDER BY cseq DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
    List<Community> findByStartnumAndEndnumAndSearch(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("search") String search);

    @Query(value = "SELECT * FROM " + "(SELECT rownum rn, tt.* FROM "
			+ "(SELECT * FROM community ORDER BY chit DESC) tt)"
			+ " WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
	List<Community> findByChit(@Param("startNum") int startNum, @Param("endNum") int endNum);    

    @Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM community ORDER BY chit DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
    List<Community> findByHit(@Param("startNum") int startNum, @Param("endNum") int endNum);

    @Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM community ORDER BY clike DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
    List<Community> findByLike(@Param("startNum") int startNum, @Param("endNum") int endNum);

    @Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM community WHERE csub LIKE %:search% OR ccon LIKE %:search% ORDER BY chit DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
    List<Community> findByHitAndSearch(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("search") String search);

    @Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM community WHERE csub LIKE %:search% OR ccon LIKE %:search% ORDER BY clike DESC) tt) WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
    List<Community> findByLikeAndSearch(@Param("startNum") int startNum, @Param("endNum") int endNum, @Param("search") String search);
    
    
    // 아이디별 커뮤니티 글 수 
    @Query(value="select count(*) from Community where cid=:id")
    Integer communitycount(@Param("id") String id);
    
    // 아이디별 커뮤니티 총 리스트
    @Query(value="select * from Community where cid=:id", nativeQuery = true)
    List<Community>  findBycid(@Param("id") String id);
    
//    List<Community> findByCsubContainingIgnoreCase(String query);
}
