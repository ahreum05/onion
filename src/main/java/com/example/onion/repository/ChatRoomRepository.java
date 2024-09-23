package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.onion.entity.Chatroom;


public interface ChatRoomRepository extends JpaRepository<Chatroom, Integer> {
	@Query(value = "SELECT * FROM chatroom WHERE (CRsendid = :senderId AND CRrecid = :receiveId) "
			+ "OR (CRsendid = :receiveId AND CRrecid = :senderId)", nativeQuery = true)
	Chatroom findByCRsendidAndCRrecid(@Param("senderId") String senderId, @Param("receiveId") String receiveId);


	@Query(value = "SELECT * FROM chatroom WHERE (CRsendid = :senderId ) "
			+ "OR ( CRrecid = :senderId)", nativeQuery = true)
	 List<Chatroom> findByCRsendid(@Param("senderId") String senderId);
	
    @Query(value="SELECT COUNT(*) FROM chatroom where(CRsendid = :id )",nativeQuery = true)
    Integer findByCount(@Param("id") String id);

    @Query(value = "SELECT COUNT(*) FROM chatroom WHERE TRUNC(CRlogtime) = TRUNC(SYSDATE) and CRsendid = :id ", nativeQuery = true)
    Integer findByDate(@Param("id") String id);

    
}
