package com.example.onion.dto;

import com.example.onion.entity.Bizcategory;
import com.example.onion.entity.Categorygroups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BizcategoryDTO {

    private Long id;
    private String bizcategory;
    private Long groupseq;


    public Bizcategory toEntity(Categorygroups categorygroup) {
        return new Bizcategory(id, bizcategory, categorygroup);
    }
}
