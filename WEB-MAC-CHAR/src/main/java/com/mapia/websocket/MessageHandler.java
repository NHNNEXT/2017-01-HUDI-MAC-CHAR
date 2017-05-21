package com.mapia.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.mapia.domain.Lobby;
import com.mapia.domain.Room;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@RestController
public class MessageHandler {
    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private Lobby lobby;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/from/chat/{roomId}")
    public ClientMessage broadcasting(ClientMessage message, @DestinationVariable long roomId) throws Exception {
        log.debug("ClientMessage arrived:/chat/{}, message: {}", roomId, message);
        return message;
    }

    @MessageMapping("/ready/{roomId}")
    @SendTo("/from/ready/{roomId}")
    public ReadySignal broadcasting(ReadySignal ready, @DestinationVariable long roomId) throws Exception {
        log.debug("ReadySignal arrived: /ready/{}, ready: {}", roomId, ready);
        Room room = lobby.getRoom(roomId);
        room.findByNickname(ready.getUserName()).toggleReady();
        if (room.isAllReady()) {
            ready.setStartTimer(true);
            room.createGameManager();
        }
        return ready;
    }

    @MessageMapping("/access/{roomId}")
    @SendTo("/from/access/{roomId}")
    public ClientAccess broadcasting(ClientAccess access, @DestinationVariable long roomId) throws Exception {
        log.debug("ClientAccess arrived: /access/{}, access: {}", roomId, access);
        Room room = lobby.getRoom(roomId);
        access.setUsers(room.getUsers());
        return access;
    }

    @MessageMapping("/gameStart/{roomId}/{userName}")
    @SendTo("/from/gameStart/{roomId}/{userName}")
    public String broadcasting(GameStart gameStart, @DestinationVariable long roomId,
                               @DestinationVariable String userName) throws Exception {
        log.debug("GameStart arrived: /gameStart/{}/{}, gameStart: {}", roomId, userName, gameStart);
        Room gameRoom = lobby.getRoom(roomId);
        gameRoom.getUsers().forEach(user -> user.gameStart());
        return gameRoom.getUserRoleNameInGame(userName);
    }
    
    @MessageMapping("/vote/{roomId}")
    @SendTo("/from/vote/{roomId}")
    public String broadcasting(VoteMessage voteMessage, @DestinationVariable long roomId) throws Exception {
        log.debug("voteMessage arrived: /vote/{}, voteMessage: {}", roomId, voteMessage);
        Room gameRoom = lobby.getRoom(roomId);
        return gameRoom.returnVoteResult(voteMessage);
//        return "testUser1";
    }
}
