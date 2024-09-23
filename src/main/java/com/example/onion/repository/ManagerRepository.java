package com.example.onion.repository;

import com.example.onion.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, String> {

    // 관리자 ID를 기준으로 검색하는 메서드
    Optional<Manager> findByMid(String mid);

    // 관리자 ID와 비밀번호를 기준으로 검색하는 메서드
    Optional<Manager> findByMidAndMpwd(String mid, String mpwd);

    // 페이지네이션을 위한 메서드
    @Query(value = "select * from " + "(select rownum rn, tt.* from " + "(select * from userInfo order by seq desc) tt) "
            + "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
    List<Manager> findByStartnumAndEndnum(@Param("startNum") int startNum, @Param("endNum") int endNum);
}
