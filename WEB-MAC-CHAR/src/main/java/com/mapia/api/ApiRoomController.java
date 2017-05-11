package com.mapia.api;

import com.mapia.domain.Lobby;
import com.mapia.domain.Room;
import com.mapia.domain.User;
import com.mapia.domain.User.Status;
import com.mapia.result.RoomResult;
import com.mapia.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Jbee on 2017. 4. 9..
 */
@RestController
@RequestMapping("/api/room")
public class ApiRoomController {
    private static final Logger log = LoggerFactory.getLogger(ApiRoomController.class);

    @Autowired
    private Lobby lobby;

    @GetMapping("/{id}")
    public RoomResult enter(@PathVariable long id, HttpSession session, Model model) {
    	
        if (!HttpSessionUtils.isLoginUser(session)) {
            return RoomResult.invalidAccess(id);
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        if (!lobby.isExistRoom(id)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            if (!user.isLobby()) {
                exitUserFromRoom(user);
            }
            return RoomResult.invalidAccess(id);
        }

        Room room = lobby.getRoom(id);
        if (!user.isAbleToEnter(id)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            exitUserFromRoom(user);
            return RoomResult.invalidAccess(id);
        }
        
        if (room.isFull()) {
            return RoomResult.rejectToEnterRoomOfFull(id);
        }

        if (room.isSecretMode()) {
            //TODO Add secret mode logic
        }

        room.enter(user);
        model.addAttribute("room", room);
        model.addAttribute("user", user);

        return RoomResult.successToEnterRoom(id);
    }

    @PostMapping("")
    public RoomResult create(String title) {
        //TODO Add secret mode logic
        long roomId  = lobby.createRoom(title);
        log.info("Created RoomId: {}", roomId);
        return RoomResult.successToCreateRoom(roomId);
    }

    @DeleteMapping("")
    public RoomResult exit(HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        long id = exitUserFromRoom(user);
        return RoomResult.successToOutRoom(id);
    }

    private long exitUserFromRoom(User user) {
        long currentRoomId = user.getEnteredRoomId();
        Room room = lobby.getRoom(currentRoomId);
        room.exit(user);
        if (room.isEmpty()) {
            lobby.delRoom(currentRoomId);
        }
        return currentRoomId;
    }
}
