package com.example.onion.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "boardinfo")
public class Boardinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "BOARDINFO_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "BOARDINFO_SEQUENCE_GENERATOR",
						sequenceName = "Bseq_boardInfo", initialValue = 1, allocationSize = 1)
	private int Bseq; 			// 글번호
	private String Bid; 		// 글작성자 아이디
	private String Bname; 		// 글작성자
	private String Bimg; 		// 글 첨부 이미지
	private String Bcategory; 	// 글 카테고리
	 @Column(name = "Bsub")
	private String bsub; 		// 글제목
	private String Bprc;		// 가격
	private String Bcon; 		// 글내용		
	private int Blike;       // 글 좋아요
	private int Bhit; 			// 글 조회수
	@Temporal(TemporalType.DATE)
	private Date Blogtime; 		// 글 작성일
}
