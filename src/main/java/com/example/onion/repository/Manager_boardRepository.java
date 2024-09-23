package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Manager_board;



public interface Manager_boardRepository extends JpaRepository<Manager_board, Integer>{
	@Query(value = "select * from"
			+ " (select rownum rn, tt.* from "
			+ " (select * from Manager_board order by MBseq desc) tt) "
			+ " where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Manager_board> findByStartnumAndEndnum(@Param("startNum") int startNum,
										@Param("endNum") int endNum);

}
