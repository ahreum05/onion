package com.example.onion.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CommentDTO {
	private int commentId;
	private int communityId;
	private String userId;
	private String content;
	private Date logtime;
}
