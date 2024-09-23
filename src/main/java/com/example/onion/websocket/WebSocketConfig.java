package com.example.onion.websocket;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Data
@Configuration // bean 객체 생성
@EnableWebSocket // 웹소켓 활성화 하기
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyWebSocketHandler myWebSocketHandler;  // 핸들러 의존성 주입
	
    
    @Override
	// registerWebSocketHandlers : WebSocket 핸들러를 특정 엔드포인트에 매핑
	// registry.addHandler 메서드 사용해 MyWebSocketHandler를 "/ws" 엔드포인트에 등록
	// 엔드포인트 : "/ws"는 클라이언트가 websocket을 통해 연결할 때 사용되는 url 패턴
	// ex) 서버가 http://localhost:8080에서 실행되면
	// 클라이언트는 ws://localhost:8080/ws로 WebSocket 연결을 시도
	// -> 난 여기서 endpoint를 chat으로 설정함
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		/////////////////////  added by mosy websocket 연결 end point 변경
		//// 사유 : restapi chatController  end point 중복
		registry.addHandler(myWebSocketHandler, "/ws/chatsocket/**").setAllowedOrigins("*");
		// setAllowedOrigins("*") : 모든 도메인에서 들어오는 websocket을 허용하겠다는 의미 (보안 취약)
		// 특정 도메인만 하려면 setAllowedOrigins("http://example.com",
		// "http://another-domain.com"); 식이 가능
		// Origin 은 Protocol, Host, Port 3개 부분으로 구성
		// http://localhost:8080/
		// > protocol : http
		// > host : localhost
		// 3개가 동일할때만 origin

	}

}
