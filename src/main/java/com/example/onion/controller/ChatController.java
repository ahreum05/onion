package com.example.onion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.onion.dto.UserInfoDTO;
import com.example.onion.entity.Boardinfo;
import com.example.onion.entity.Chatmessage;
import com.example.onion.entity.Chatroom;
import com.example.onion.entity.Saleuser;
import com.example.onion.service.ChatMessageService;
import com.example.onion.service.ChatRoomService;
import com.example.onion.service.SaleService;
import com.example.onion.service.UserService;
import com.example.onion.service.boardInfoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ChatController {

	@Autowired
	ChatRoomService crs;

	@Autowired
	ChatMessageService cms;

	@Autowired
	UserService userService;

	@Autowired
	boardInfoService bis;

	@Autowired
	SaleService ss;

	@GetMapping("/chat")
	public String chat(Model model, HttpServletRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = "anonymous";
		if (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) {
			// 인증된 사용자의 경우 이름을 가져옴
			username = authentication.getName();
			UserInfoDTO userInfo = userService.getMemberById1(username);
			String id = userInfo.getUserid();
			String name = userInfo.getUname();

			model.addAttribute("userid", id);
			model.addAttribute("username", name);
		}

		String uid = authentication.getName();

		// UserService의 getMemberById1 메서드를 통해 userid와 uname을 불러옴
		UserInfoDTO userInfo = userService.getMemberById1(uid);

		String senderId = userInfo.getUserid();
		String senderName = userInfo.getUname();

		// 판매자 정보쪽에서 채팅을 눌렀을 경우만 해당
		if (request.getParameter("foodmarket") != null && !request.getParameter("foodmarket").equals("")) {
			String foodmarket = request.getParameter("foodmarket");
			System.out.println("foodmarket : " + foodmarket);

			Saleuser user = ss.SaleInfo(foodmarket);

			String receiveId = foodmarket;
			String receiveName = user.getSalestorename();

			// 채팅방 생성하기
			// 그전에 채팅방이 있는지 확인해 보기
			if (receiveId != null) {
				Chatroom chatRoomInfo = crs.ChatRoomnumber(senderId, receiveId);
				System.out.println("chatRoomInfo : " + chatRoomInfo);

				int chatRoomNumber = chatRoomInfo.getCRroomnum();
				model.addAttribute("chatRoomInfo", chatRoomInfo);
				model.addAttribute("chatRoomNumber", chatRoomNumber);
				model.addAttribute("receiveName", receiveName);
				model.addAttribute("receiveId", receiveId);
			}
		}

		// 게시판에서 채팅을 눌렀을 경우만 해당 -> 채팅방 생성 있으면 있는거
		if (request.getParameter("seq") != null && !request.getParameter("seq").equals("")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			System.out.println("seq : " + seq);

			Boardinfo boardInfo = bis.boardInfoView(seq);
			String receiveId = boardInfo.getBid();
			String receiveName = boardInfo.getBname();
			System.out.println("boardInfo : " + boardInfo);

			// 채팅방 생성하기
			// 그전에 채팅방이 있는지 확인해 보기
			if (receiveId != null) {
				Chatroom chatRoomInfo = crs.ChatRoomnumber(senderId, receiveId);
				System.out.println("chatRoomInfo : " + chatRoomInfo);

				int chatRoomNumber = chatRoomInfo.getCRroomnum();
				model.addAttribute("chatRoomInfo", chatRoomInfo);
				model.addAttribute("chatRoomNumber", chatRoomNumber);
				model.addAttribute("receiveName", receiveName);
				model.addAttribute("receiveId", receiveId);
			}
		}

		// 채팅 리스트 목록 보여주기
		List<Chatroom> chatlist = crs.chatRoomList(senderId);

		// 받은 데이터를 Model에 추가

		model.addAttribute("senderId", senderId);
		model.addAttribute("senderName", senderName);
		model.addAttribute("chatlist", chatlist);
		return "/chat/Chatroom"; // View 이름 반환
	}

	@GetMapping("/ws/chat/{chatRoomNumber}")
	public ResponseEntity<List<Chatmessage>> getChatMessages(@PathVariable("chatRoomNumber") int chatRoomNumber) {
		List<Chatmessage> messages = cms.getMessagesByRoomNum(chatRoomNumber);
		System.out.println("controller  - entity  - messages : " + messages);
		// 메시지와 함께 200 OK 상태를 반환
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}

	
	// 판매자 측 채팅
	@GetMapping("/sale/chat")
	public String salerchat(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String senderId = (String) session.getAttribute("Sid");

		Saleuser saleuser = ss.SaleInfo(senderId);
		String senderName = saleuser.getSalestorename();
		
		// 채팅 리스트 목록 보여주기
		List<Chatroom> chatlist = crs.chatRoomList(senderId);

		// 받은 데이터를 Model에 추가

		model.addAttribute("senderId", senderId);
		model.addAttribute("senderName", senderName);
		model.addAttribute("chatlist", chatlist);
		return "/sale/Chatroom"; // View 이름 반환
	}

	
}
