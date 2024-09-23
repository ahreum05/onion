package com.example.onion.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.example.onion.entity.Chatroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
	int CRroomnum;
	String CRsendid;
	String CRrecid;
	Date CRlogtime;

	public Chatroom toEntity() {
		return new Chatroom(CRroomnum, CRsendid, CRrecid, CRlogtime);
	}
}
