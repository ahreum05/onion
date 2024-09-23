package com.example.onion.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onion.dto.CommentDTO;
import com.example.onion.entity.Comment;
import com.example.onion.repository.CommentRepository;

@Repository
public class CommentDAO {
	@Autowired
	private CommentRepository commentRepository;

	public void addComment(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setCommunityId(commentDTO.getCommunityId());
		comment.setUserId(commentDTO.getUserId());
		comment.setContent(commentDTO.getContent());
		comment.setLogtime(new Date()); // 작성 시간 설정
		commentRepository.save(comment);
	}

	public List<Comment> getCommentsByCommunityId(int communityId) {
		return commentRepository.findCommentsByCommunityIdOrderByLogtimeDesc(communityId);
	}

	public Comment getCommentById(int commentId) {
		return commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
	}

	// 댓글 삭제 메서드 추가
	public void deleteComment(int commentId) {
		commentRepository.deleteById(commentId);
	}
	// 사용자 아이디별 댓글 수 
		public int count(String Cid) {
			Integer count=commentRepository.commentcount(Cid);
			if(count ==null) {
				count = 0;
			}
			return count;
		}

}