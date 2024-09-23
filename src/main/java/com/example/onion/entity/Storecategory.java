package com.example.onion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storecategory {

	@Id
	private int SCseq ;
	private String SCname;
	private int  Bizid ;
	
}
