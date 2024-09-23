package com.example.onion.dto;

import java.util.Date;

import com.example.onion.entity.Menuitems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuitemsDTO {
	private String MIid;
	private int MIseq;
	private String MIitemname;
	private int MIprice;
	private String MIimg;
	private int MIhit;
	private int is_available;
	private Date MIlogtime;

	public Menuitems toEntity() {
		return new Menuitems(MIid, MIseq, MIitemname, MIprice, MIimg, MIhit, is_available, MIlogtime);
	}

}
