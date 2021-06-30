package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.model.ChatMessage;


@Component
public class WebSocketEventListener {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
	private SimpMessageSendingOperations messangingTemplate;
	
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event){
		
		logger.info("Received a new web socket connection");
		
	}
	
	@EventListener
	public void handlewebSocketDisconnecctedListener(SessionDisconnectEvent event) {
		
		StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
		
		String username = (String) headerAccesor.getSessionAttributes().get("username");
		
		if(username != null) {
			logger.info("user Disconnected : " + username);
			
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType(ChatMessage.MessageType.LEAVE);
			
			messangingTemplate.convertAndSend("/topic/public",chatMessage);
		}
	}
	
	

}
