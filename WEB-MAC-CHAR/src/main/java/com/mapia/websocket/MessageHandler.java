package com.mapia.websocket;

import java.util.logging.Logger;

import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mapia.web.LobbyController;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@RestController
public class MessageHandler {
	@MessageMapping("/chat/{roomId}")
	@SendTo("/from/chat/{roomId}")
	public ClientMessage broadcasting(ClientMessage message) throws Exception {
		System.out.println(message.getContent());
		return message;
	}
	
	@MessageMapping("/ready/{roomId}")
	@SendTo("/from/ready/{roomId}")
    private GameStartSignal broadcasting(ReadySignal readySignal) throws Exception{
    	return new GameStartSignal(true);
	}
}
