package com.example.onion.dto;

import java.util.Date;

import com.example.onion.entity.Saleuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDTO {
	private String Saleid;
	private String Saletel;
	private String Salepwd;
	private String Saleemail;
	private String Saleceo;
	private String Salecompanyname;
	private String Salestorename;
	private String Salestoretel;
	private String Saleurl;
	private String Salecategory;
	private String Saleimage;
	private String Saleopen;
	private String Saleclose;
	private String Salebusinessnum;
	private String Saleaddress;
	private String Salebusinessimg;
	private Date Salelogtime;

	public Saleuser toEntity() {
	    return new Saleuser(Saleid, Saletel, Salepwd, Saleemail, Saleceo, Salecompanyname, 
	                        Salestorename, Salestoretel, Saleurl, Salecategory, Saleimage, 
	                        Saleopen, Saleclose, Salebusinessnum, Saleaddress, 
	                        Salebusinessimg, Salelogtime);
	}

}
