package com.example.onion.dto;

import java.time.LocalDateTime;

import com.example.onion.entity.Userinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
	private Userinfo user;
    private int totalA;  // 게시글 수
    private int totalComments;  // 댓글 수

}