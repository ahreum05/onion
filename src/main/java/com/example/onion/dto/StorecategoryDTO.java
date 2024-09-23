package com.example.onion.dto;

import com.example.onion.entity.Storecategory;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorecategoryDTO {
	
	private int SCseq ;
	private String SCname;
	private int  Bizid ;
	
	public Storecategory toEntity() {
		return new Storecategory(SCseq,SCname, Bizid);
	}
}
