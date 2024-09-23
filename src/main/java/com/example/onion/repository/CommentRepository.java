package com.example.onion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.onion.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	@Query("SELECT c FROM Comment c WHERE c.communityId = :communityId ORDER BY c.logtime DESC")
    List<Comment> findCommentsByCommunityIdOrderByLogtimeDesc(@Param("communityId") int communityId);
    // 커뮤니티 ID로 댓글 삭제
    void deleteByCommunityId(int communityId);
    
    
    @Query("select count(*) from Comment where userId=:id")
    Integer commentcount (@Param ("id") String id);
}


