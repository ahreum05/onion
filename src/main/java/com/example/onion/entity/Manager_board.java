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
public class Manager_board {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MBseq_num_generator")
    @SequenceGenerator(name = "MBseq_num_generator", sequenceName = "MBseq_num", allocationSize = 1)
	
	private int MBseq;      
	private String MBid;
    private String MBsub;
    private String MBcon;
    private String MBimg; 
    private int MBhit;
    @Temporal(TemporalType.DATE)
    private Date MBlogtime; 

}
