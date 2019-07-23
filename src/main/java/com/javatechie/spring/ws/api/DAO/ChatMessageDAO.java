package com.javatechie.spring.ws.api.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javatechie.spring.ws.api.model.ChatMessage;

public interface ChatMessageDAO extends JpaRepository<ChatMessage, String>{
	
	@Query("Select c from ChatMessage c where c.sender = :sender")
	public ChatMessage findBySenderUser(@Param("sender") String sender);

}
