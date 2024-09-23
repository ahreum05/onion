package com.example.onion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boardcategory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_boardcategory")
	@SequenceGenerator(name = "seq_boardcategory", sequenceName = "seq_Boardcategory", initialValue = 1, allocationSize = 1)
	private Long id;

	private String Boardcategory;
	private String Boardcategoryimg;
	
	@ManyToOne
    @JoinColumn(name = "groupseq", referencedColumnName = "groupseq")
	private Categorygroups categorygroup;
}