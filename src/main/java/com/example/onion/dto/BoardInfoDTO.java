package com.example.onion.dto;

import java.util.Date;

import com.example.onion.entity.Boardinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoardInfoDTO {
	private int Bseq; 			// 글번호
	private String Bid; 		// 글작성자 아이디
	private String Bname; 		// 글작성자
	private String Bimg; 		// 글 첨부 이미지
	private String Bcategory; 	// 글 카테고리
	private String Bsub; 		// 글제목
	private String Bprc;		// 가격
	private String Bcon; 		// 글내용		
	private int Blike;       // 글 좋아요
	private int Bhit; 			// 글 조회수
	private Date Blogtime; 		// 글 작성일
	
	public Boardinfo toEntity() {
		return new Boardinfo(Bseq, Bid, Bname, Bimg, Bcategory, Bsub, Bprc, Bcon, Blike, Bhit, Blogtime);
	}

}
