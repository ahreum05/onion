package com.example.onion.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Saleuser {
	@Id
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
	@Temporal(TemporalType.DATE)
	private Date Salelogtime;

}
