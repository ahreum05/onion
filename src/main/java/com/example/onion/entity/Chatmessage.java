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
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Chatmessage {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chatmessage_generator")
    @SequenceGenerator(name = "seq_chatmessage_generator", sequenceName = "seq_chatmessage", allocationSize = 1)
	int CMmsgnum;
	int CMroomnum;
	String CMsendid;
	String CMsendmsg;
	Date CMsendtime;
}
