package com.example.onion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onion.entity.Chatmessage;
import com.example.onion.repository.ChatMessageRepository;


@Service
public class ChatMessageService {
	@Autowired
	private ChatMessageRepository cmr;


	public List<Chatmessage> getMessagesByRoomNum(int chatRoomNumber){
		List<Chatmessage> list =  cmr.findByCMroomnumOrderByCMroomnumeAsc(chatRoomNumber);
		System.out.println("<ChatMessageSercie>");
		System.out.println("chatRoomNumber :  "+chatRoomNumber);
		System.out.println(" list : "+list );
		System.out.println("----------------");
		System.out.println();

		return list;
	}

	   public Chatmessage saveMessage(Chatmessage message) {
	        return cmr.save(message);  
	    }
	
	
}
