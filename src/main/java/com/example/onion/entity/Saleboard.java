package com.example.onion.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Saleboard {
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

}
