package com.example.onion.dto;

import java.util.Date;

import com.example.onion.entity.Jobboard;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobboardDTO {
	private String JBid;
	private int JBseq;
	private String  JBsub ;
	private String JBcon;
	private int JBPeoplenum;
	private String JBWorkingdate;
	private String JBWorkinghours;
	private String JBEnddate;
	private String JBSalarytype;
	private String JBSalary;
	private int is_available;
	private Date JBlogtime;
	
	public Jobboard toEntity() {
		return new Jobboard(JBid, JBseq, JBsub, JBcon, JBPeoplenum, JBWorkingdate, JBWorkinghours, JBEnddate, JBSalarytype, JBSalary, is_available, JBlogtime, null);
	}
}
