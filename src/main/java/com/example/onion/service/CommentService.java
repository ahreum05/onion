package com.example.onion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.onion.dao.CommentDAO;
import com.example.onion.dto.CommentDTO;
import com.example.onion.entity.Comment;
import com.example.onion.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentDAO dao;

	public void addComment(CommentDTO commentDTO) {
		dao.addComment(commentDTO); // 댓글 저장
	}

	public List<Comment> getCommentsByCommunityId(int communityId) {
        return commentRepository.findCommentsByCommunityIdOrderByLogtimeDesc(communityId); // 시간순 정렬
    }

	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	public void deleteCommentsByCommunityId(int communityId) {
		commentRepository.deleteByCommunityId(communityId);
	}

	public Comment getCommentById(int commentId) {
		return commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
	}

	@Transactional
	public void updateComment(CommentDTO commentDTO) {
		Comment comment = commentRepository.findById(commentDTO.getCommentId())
				.orElseThrow(() -> new RuntimeException("Comment not found"));
		comment.setContent(commentDTO.getContent());
		commentRepository.save(comment);
	}

	// 댓글 삭제 메서드 추가
	public void deleteComment(int commentId) {
		dao.deleteComment(commentId); // DAO에서 댓글 삭제 호출
	}
	
	// 사용자 아이디별 댓글 수 
	public int count(String Cid) {
		return dao.count(Cid);
	}
}
