package com.example.onion.dto;

import java.util.Date;
import java.util.List;

import com.example.onion.entity.Community;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class CommunityDTO {
    private int cseq; 						// 글번호
    private String cid;						// 아이디
    private String cname;					// 글작성자
    private String csub; 					// 글제목
    private String ccon; 					// 글내용
    private String cimg; 					// 글 첨부 이미지
    private int clike; 						// 글 좋아요 수
    private int chate;						// 글 싫어요 수
    private int chit; 						// 글 조회수
    private Date clogtime;					// 글 작성일
    
    
    public Community toEntity() {
    	return new Community(cseq, cid, cname, csub, ccon, cimg, clike, chate, chit, clogtime);
    }
}
