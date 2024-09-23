package com.example.onion.dto;

import com.example.onion.entity.Boardcategory;
import com.example.onion.entity.Categorygroups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BoardcategoryDTO {

    private Long id;

	private String Boardcategory;
	private String Boardcategoryimg;
    private Long groupseq;



    public Boardcategory toEntity(Categorygroups categorygroup) {
        return new Boardcategory(id, Boardcategory,Boardcategoryimg ,categorygroup);
    }
}
