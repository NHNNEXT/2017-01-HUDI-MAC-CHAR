package com.mapia.websocket;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.mapia.domain.GameManager;
import com.mapia.domain.Lobby;
import com.mapia.domain.Room;
import com.mapia.domain.User;
import com.mapia.domain.role.Role;

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
    	Room room = lobby.getRoom(roomId);
    	room.findByNickname(ready.getUserName()).toggleReady();
    	if(room.isAllReady()) {
    		ready.setStartTimer(true);
    		room.createGameManager();
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
    
    @MessageMapping("/gameStart/{roomId}/{userName}")
    @SendTo("/from/gameStart/{roomId}/{userName}")
    public String broadcasting(GameStart gameStart, @DestinationVariable long roomId) throws Exception {
    	GameManager gameManager = lobby.getRoom(roomId).getGameManager();
    	Role role = gameManager.findRoleNameByUserName(gameStart.getUserName());
    	log.info("{}: {}", gameStart.getUserName(), role.getRoleName());
    	return role.getRoleName().toString();
    }
}
