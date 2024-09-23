package com.example.onion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.entity.Chatroom;
import com.example.onion.repository.ChatRoomRepository;


@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomRepository crr;

	public Chatroom ChatRoomnumber(String CRsendid, String CRrecid) {
		System.out.println("CRsendid : "+CRsendid);
		System.out.println("CRrecid : "+CRrecid);
		
		Chatroom chatroom = crr.findByCRsendidAndCRrecid(CRsendid, CRrecid);
		
		if (chatroom == null) {
		
			Chatroom chatroom1 = new Chatroom();
			chatroom1.setCRsendid(CRsendid);
			chatroom1.setCRrecid(CRrecid);
			chatroom1.setCRlogtime(new Date());
			
			return crr.save(chatroom1);
		}

		return chatroom;
	}
	

	public List<Chatroom> chatRoomList(String CRsendid){
		return crr.findByCRsendid(CRsendid);
	}
	
	
	// 채팅 총 수 
	public int chatcount(String id) {
		return crr.findByCount(id);
	}
	
	// 오늘의 채팅 수 
	public int chatdatecount(String id) {
		return crr.findByDate(id);
	}
}
