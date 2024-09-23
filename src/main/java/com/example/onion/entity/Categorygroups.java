package com.example.onion.entity; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Lombok 어노테이션으로 Getter, Setter, toString 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 자동 생성
public class Categorygroups {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categorygroups")
    @SequenceGenerator(name = "seq_categorygroups", sequenceName = "seq_Categorygroups", initialValue = 1, allocationSize = 1)
    private Long groupseq;

    private String groupname;
}
