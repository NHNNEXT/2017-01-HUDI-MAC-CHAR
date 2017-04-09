package com.mapia.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@RestController
public class MessageHandler {

    @MessageMapping("/hello")
    @SendTo("/topic/1")
    public ClientMessage broadcasting(ClientMessage message) throws Exception {
        return message;
    }
    
    @MessageMapping("/room/{roomId}")
    @SendTo("/topic/{roomId}")
    public ClientMessage messageToRoom(ClientMessage message, @DestinationVariable String roomId) throws Exception {
    	return message;
    }
    
}
