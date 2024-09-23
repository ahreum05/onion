package com.example.onion.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.example.onion.entity.Chatmessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
	int CMmsgnum;
	int CMroomnum;
	String CMsendid;
	String CMrecvid; 
	String CMsendmsg;
	Date CMsendtime;

	public Chatmessage toEntity() {
		return new Chatmessage(CMmsgnum, CMroomnum, CMsendid, CMsendmsg, CMsendtime);
	}
}
