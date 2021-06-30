package com.example.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.example.model.ChatMessage;

@Controller
public class ChatController {
	
	
	// meassage with destination /app/chat.sendMessage
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}
	
	
	// message with destination /app/chat.addUser
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, 
			SimpMessageHeaderAccessor headerAccesor){
		//Add username in web socket session
		
		headerAccesor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
		
	}
	
	

}
