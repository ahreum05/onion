package com.example.onion.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SequenceGenerator(name = "community_seq", sequenceName = "seq_community", allocationSize = 1)
public class Community {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "community_seq")
	private int cseq; 						// 글번호
    private String cid;						// 아이디
    private String cname;					// 글작성자
    private String csub; 					// 글제목
    private String ccon; 					// 글내용
    private String cimg; 					// 글 첨부 이미지
    private int clike; 						// 글 좋아요 수
    private int chate;						// 글 싫어요 수
    private int chit; 						// 글 조회수
    @Temporal(TemporalType.DATE)
    private Date clogtime;					// 글 작성일
}
