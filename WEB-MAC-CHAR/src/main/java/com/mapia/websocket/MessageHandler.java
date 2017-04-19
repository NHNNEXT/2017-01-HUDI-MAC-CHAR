package com.mapia.websocket;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.mapia.domain.Lobby;
import com.mapia.domain.Room;
import com.mapia.domain.User;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@RestController
public class MessageHandler {
	private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);
	
	@Autowired
	Lobby lobby;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/from/chat/{roomId}")
    public ClientMessage broadcasting(ClientMessage message) throws Exception {
        return message;
    }
    
    @MessageMapping("/ready/{roomId}")
    @SendTo("/from/ready/{roomId}")
    public ReadySignal broadcasting(ReadySignal ready, @DestinationVariable long roomId) throws Exception {
    	lobby.getRoom(roomId).findByNickname(ready.getUserName()).toggleReady();
    	if(lobby.getRoom(roomId).isAllReady()) {
    		ready.setStartTimer(true);
    	}
    	return ready;
    }
    
    @MessageMapping("/access/{roomId}")
    @SendTo("/from/access/{roomId}")
    public ClientAccess broadcasting(ClientAccess access, @DestinationVariable long roomId) throws Exception {
    	Room room = lobby.getRoom(roomId);
    	Set<User> userSet = room.getUsers();
    	access.setUsers(userSet);
    	return access;
    }
}
