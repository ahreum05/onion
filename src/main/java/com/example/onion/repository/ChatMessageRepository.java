package com.example.onion.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.onion.entity.Chatmessage;

public interface ChatMessageRepository extends JpaRepository<Chatmessage, Integer> {

	@Query(value = "SELECT * FROM chatmessage WHERE CMroomnum = :CMroomnum ORDER BY CMMSGNUM ASC", nativeQuery = true)
    List<Chatmessage> findByCMroomnumOrderByCMroomnumeAsc(@Param("CMroomnum") int CMroomnum);
}
