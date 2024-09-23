package com.example.onion.repository;

import com.example.onion.entity.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Userinfo, String> {

    // 아이디와 비밀번호로 유저 찾기
    Optional<Userinfo> findByUseridAndUpwd(String userid, String upwd);

    // 아이디로 유저 찾기
    Optional<Userinfo> findByUserid(String userid);

    // 페이징을 위한 유저 목록 가져오기 (startNum과 endNum을 기준으로)
    @Query(value = "SELECT * FROM (SELECT rownum rn, tt.* FROM (SELECT * FROM userInfo ORDER BY Userid DESC) tt) "
            + "WHERE rn >= :startNum AND rn <= :endNum", nativeQuery = true)
    List<Userinfo> findByStartnumAndEndnum(@Param("startNum") int startNum, @Param("endNum") int endNum);

    // 전체 사용자 수 카운트
    @Query(value = "SELECT COUNT(*) FROM userInfo", nativeQuery = true)
    long countTotalUsers();

    // 전체 유저 목록
    List<Userinfo> findAll();
    
}
