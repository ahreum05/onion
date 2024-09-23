package com.example.onion.dto;

import java.util.Date;

import com.example.onion.entity.Saleboard;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleboardDTO {
	private String SBid;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_saleboard_generator")
	@SequenceGenerator(name = "seq_saleboard_generator", sequenceName = "seq_saleboard", allocationSize = 1)
	private int SBseq;
	private String SBsubject;
	private String SBcontent;
	private String SBimg;
	private int is_available;

	private Date SBlogtime;

	public Saleboard toEntity() {
		return new Saleboard(SBid, SBseq, SBsubject, SBcontent, SBimg, is_available, SBlogtime);
	}
}
