package com.example.onion.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jobboard {


	private String JBid;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Jobboard_generator")
	@SequenceGenerator(name = "seq_Jobboard_generator", sequenceName = "seq_Jobboard", allocationSize = 1)
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
	@Temporal(TemporalType.DATE)
	private Date JBlogtime;
	
	 // Jobboard와 SaleUser 간의 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)  // 필요에 따라 EAGER로 변경 가능
    @JoinColumn(name = "JBid", referencedColumnName = "Saleid", insertable = false, updatable = false)
    private Saleuser saleuser;  // SaleUser 엔티티와의 관계
    

}