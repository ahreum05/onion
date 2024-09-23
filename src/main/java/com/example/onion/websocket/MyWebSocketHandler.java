package com.example.onion.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.onion.entity.Chatmessage;
import com.example.onion.repository.ChatMessageRepository;
import com.example.onion.service.ChatMessageService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j2;

@Component // 롬복 라이브러리 : 자동으로 Spring 컨텍스트의 빈(Bean)으로 등록
@Log4j2 // 롬복 애노테이션 : 로그를 사용하여 애플리케이션의 실행 중 발생하는 이벤트를 기록
public class MyWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private ChatMessageService chatMessageService;

	// 세션을 저장하는 Map, chatRoomNumber를 기준으로 세션을 분리
	private final Map<String, Map<String, WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
	@Autowired
	private ChatMessageRepository cmr;

	// WebSocketSession session : 클라이언트와의 연결 관리
	// textMessage message : 클라이언트로 부터 수신한 텍스트 메시지 나오게 함
	// message.getPlayload() : 클라이언트가 보낸 메세지의 본문을 가져옴
	// session.sendMessage(new TextMessage("receive"+ payload)); : 클라이언트에게 "receive
	// "이라는 접두사와 함께 메시지를 다시 전송
	// 제대로 송수신이 이뤄지는지 테스트
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		System.out.println("클라이언트 연결 됨 ");

		// 페이로드란 전송되는 데이터를 의미
		String payload = message.getPayload();
		log.info("Received payload: " + payload);

		try {

			if (payload.trim().startsWith("{")) {
				// JSON 데이터에서 필요한 정보 추출
				JSONObject originObject = new JSONObject(payload);
				String type = originObject.getString("type");
				// 세션에서 chatRoomNumber 가져오기
				String chatRoomNumber = (String) session.getAttributes().get("chatRoomNumber");
				Map<String, WebSocketSession> sessionsInRoom = chatRooms.get(chatRoomNumber);

			 if ("sent".equals(type)) {
					// 메시지 전송 로직 처리 (content 필요)
					String senderId = originObject.getString("senderId");
					String content = originObject.getString("content");
					// 메시지 전송 로직...
					log.info("Message from {}: {}", senderId, content);

					Chatmessage chatMessage = new Chatmessage();
					chatMessage.setCMroomnum(Integer.parseInt(chatRoomNumber));
					chatMessage.setCMsendid(senderId);
					chatMessage.setCMsendmsg(content);
					chatMessage.setCMsendtime(new Date()); // 메시지 보낸 시간
					chatMessageService.saveMessage(chatMessage); // DB에 저장

					if (sessionsInRoom != null) {
						for (WebSocketSession sess : sessionsInRoom.values()) {
							if (!sess.getId().equals(session.getId())) { // 본인을 제외한 다른 클라이언트들에게만 전송


								log.debug("originObject debug : {}", originObject);
								// json 데이터 정상 변환하는 코드 : content에 챗팅 메시지 내용 저장됨
					            String receivedWebMessage = "{\"type\":\"received\",\"senderId\":\"" + senderId + "\",\"content\":\"" + content + "\"}";
					            
					            // 클라이언트들에게 전송
					            sess.sendMessage(new TextMessage(receivedWebMessage));

							}
						}
					} else {
						// JSON 형식이 아닌 메시지에 대한 처리
						session.sendMessage(new TextMessage(payload));
					}
				}
			}

		} catch (

		Exception e) {
			log.error("문제 발생 : ", e);
		}
	}

	// 클라이언트가 접속 시
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		// WebSocket 연결 시 URL에서 chatRoomNumber 추출'
		// ex) /ws/chat/123
		String path = session.getUri().getPath();
		String[] pathSegments = path.split("/");
		// 마지막 세그먼트가 채팅방 번호
		String chatRoomNumber = pathSegments[pathSegments.length - 1];

		if (chatRoomNumber == null) {
			log.error("Chat room number is missing, closing session.");
			session.close();
			return;
		}

		///////////////////// added by mosy 세션 속성에 chatRoomNumber 설정 added by mosy
		session.getAttributes().put("chatRoomNumber", chatRoomNumber);

		// 새로운 방이 생성된 경우, 방을 Map에 추가?
		chatRooms.computeIfAbsent(chatRoomNumber, k -> new ConcurrentHashMap<>());
		chatRooms.get(chatRoomNumber).put(session.getId(), session);

		log.info("새로운 클라이언트가 방 {}에 연결되었습니다. 세션ID: {}", chatRoomNumber, session.getId());
	}

	// Client가 접속 해제 시
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String chatRoomNumber = (String) session.getAttributes().get("chatRoomNumber");

		if (chatRoomNumber != null) {
			Map<String, WebSocketSession> sessionsInRoom = chatRooms.get(chatRoomNumber);
			if (sessionsInRoom != null) {
				sessionsInRoom.remove(session.getId());

				// 방에 남은 세션이 없으면 방 제거
				if (sessionsInRoom.isEmpty()) {
					chatRooms.remove(chatRoomNumber);
				}
			}
		}

		log.info("클라이언트가 방 {}에서 연결 종료. 세션ID: {}, 상태: {}", chatRoomNumber, session.getId(), status);
	}
}
