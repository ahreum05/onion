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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menuitems {
	
	private String MIid;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chatroom_generator")
    @SequenceGenerator(name = "seq_chatroom_generator", sequenceName = "seq_chatroom", allocationSize = 1)
	private int MIseq;
	private String MIitemname;
	private int MIprice;
	private String MIimg;
	private int MIhit;
	private int is_available;
	@Temporal(TemporalType.DATE)
	private Date MIlogtime ;
}
