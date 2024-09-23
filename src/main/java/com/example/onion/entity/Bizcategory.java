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

public class Bizcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bizcategory")
    @SequenceGenerator(name = "seq_bizcategory", sequenceName = "seq_Bizcategory", initialValue = 1, allocationSize = 1)
    private Long id;

    private String bizcategory;

    @ManyToOne
    @JoinColumn(name = "groupseq", referencedColumnName = "groupseq")
    private Categorygroups categorygroup;
}
