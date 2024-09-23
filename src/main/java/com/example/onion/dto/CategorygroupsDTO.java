package com.example.onion.dto;

import com.example.onion.entity.Categorygroups;

import groovy.transform.builder.Builder;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorygroupsDTO {
	
	private Long groupseq;
	private String groupname;

	public Categorygroups toentity() {
		return new Categorygroups(groupseq, groupname);
	}
}
