package com.mapia.websocket;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.mapia.domain.Lobby;
import com.mapia.domain.User;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@RestController
public class MessageHandler {
	@Autowired
	Lobby lobby;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/from/chat/{roomId}")
    public ClientMessage broadcasting(ClientMessage message) throws Exception {
        return message;
    }
    
    @MessageMapping("/ready/{roomId}")
    @SendTo("/from/ready/{roomId}")
    public ClientReady broadcasting(ClientReady ready) throws Exception {
    	return ready;
    }
    
    @MessageMapping("/access/{roomId}")
    @SendTo("/from/access/{roomId}")
    public ClientAccess broadcasting(ClientAccess access, @DestinationVariable long roomId) throws Exception {
    	Set<User> userSet = lobby.getRoom(roomId).getUsers();
    	access.setUsers(userSet);
    	return access;
    }
}
