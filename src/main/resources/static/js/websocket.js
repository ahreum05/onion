// javascript websocket 코드
	document.addEventListener("DOMContentLoaded",function(){
		const ws= new WebSocket("ws://localhost:8080/ws/chat");
	//	const ws= new WebSocket("ws://112.221.246.164:8080/ws/chat");
		
		ws.onmessage = function(event){
			console.log("message from server: ",event.data);
			// 새로운 메세지 msgArea에 추가 
			 var msgArea = document.getElementById("msgArea");
		     var newMessage = document.createElement("div");
			 var now = new Date();
			 var hour = now.getHours();
			 var min = now.getMinutes(); 
			 
			 var current = hour+":"+min;
			 
		     newMessage.textContent = event.data +"("+current+")";
		     msgArea.appendChild(newMessage);
		        // 스크롤을 자동으로 맨 아래로 이동
		     msgArea.scrollTop = msgArea.scrollHeight;
		};
		
		ws.onopen = function(){
			console.log("messageconnection established. ");
			ws.send("님과 대화를 시작합니다.")
		};
		
		ws.onclose = function(event){
			console.log("message from server : ",event.reason);
		};
		
		ws.onerror = function(error){
			console.error("Websocket error : "+error);
		}
		
		 // 버튼 클릭 시 메시지 전송
	    document.getElementById("button-send").addEventListener("click", function() {
	        var msgInput = document.getElementById("msg");
	        var message = msgInput.value;
	        if (message.trim() !== "") { // 공백 메시지 방지
	            ws.send(message);              // 서버로 내 메세지 전송
	            msgInput.value = ""; // 메시지 보낸 뒤에는 입력란을 빈칸으로
	        }
	    });

	    // 엔터키를 누르면 메시지 전송
	    document.getElementById("msg").addEventListener("keydown", function(event) {
	        if (event.key === "Enter") {
	            event.preventDefault();
	            document.getElementById("button-send").click();
	        }
	    });
		
	function displayMessage(message,type){
		var msgArea = document.getElementById("msgArea");
		var newMessage = document.createElement("div");
		
		if (type=="sent"){
			newMessage.classList.add("msg-sent");
		}else{
			newMessage.classList.add("msg-received");
		}
		
		newMessage.textContent = message;
		    msgArea.appendChild(newMessage);
		    msgArea.scrollTop = msgArea.scrollHeight; // 스크롤을 자동으로 맨 아래로 이동
		
	}	
		
		
	});
	
	