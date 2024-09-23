package com.example.onion.dto;

import java.util.Date;

import com.example.onion.entity.Manager_board;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Manager_boardDTO {
	
	private int MBseq;     
	private String MBid;
    private String MBsub;
    private String MBcon;
    private String MBimg; 
    private int MBhit;
    private Date MBlogtime;
    
    public Manager_board toEntity() {
    	return new Manager_board(MBseq,MBid, MBsub, MBcon, MBimg, MBhit, MBlogtime);
    }


}
