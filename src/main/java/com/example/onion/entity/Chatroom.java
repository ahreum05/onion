package com.example.onion.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chatroom {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chatroom_generator")
    @SequenceGenerator(name = "seq_chatroom_generator", sequenceName = "seq_chatroom", allocationSize = 1)
	int CRroomnum;
	String CRsendid;
	String CRrecid;
	Date CRlogtime;
}
