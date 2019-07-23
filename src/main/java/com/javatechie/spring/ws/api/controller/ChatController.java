package com.javatechie.spring.ws.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.javatechie.spring.ws.api.DAO.ChatMessageDAO;
import com.javatechie.spring.ws.api.model.ChatMessage;

@Controller
public class ChatController {

	
	@Autowired
	ChatMessageDAO chatMessageDAO;
	
	public ChatMessage userChat = new ChatMessage();
	
	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		//System.out.println(headerAccessor.getSessionAttributes().get("username"));
		userChat.setContent("");
		userChat.setSender(headerAccessor.getSessionAttributes().get("username").toString());
		chatMessageDAO.save(userChat);
		//System.out.println("==============");
		return chatMessage;
	}
	
	@PostMapping(value = "/save")
	public ChatMessage save(@ModelAttribute  ChatMessage chatMessage) {
		chatMessageDAO.save(chatMessage);
		return chatMessage;
	}

	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		String contentInit = userChat.getContent();
		System.out.println(chatMessage.getContent().toString());
		System.out.println(userChat.getSender());
		userChat.setContent(contentInit + chatMessage.getContent().toString()+";");
		System.out.println(userChat.getContent());
		chatMessageDAO.save(userChat);
		return chatMessage;
	}

}
