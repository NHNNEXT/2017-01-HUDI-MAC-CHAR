package com.mapia.web;

import com.mapia.domain.Room;
import com.mapia.domain.Lobby;
import com.mapia.domain.User;
import com.mapia.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Jbee on 2017. 4. 4..
 */
@Controller
@RequestMapping("/room")
public class RoomController {
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private Lobby lobby;

    @GetMapping("/{id}")
    public String enter(@PathVariable long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/login";
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        if (!lobby.isExistRoom(id)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

            if (!user.isLobby()) {
                exitUserFromRoom(user);
            }
            return "redirect:/";
        }

        Room room = lobby.getRoom(id);
        if (!user.isAbleToEnter(id)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            exitUserFromRoom(user);
            return "redirect:/";
        }

        if (room.isFull()) {
            return "redirect:/lobby";
        }

        if (room.isSecretMode()) {
            //TODO Add secret mode logic
        }

        room.enter(user);
        model.addAttribute("room", room);
        model.addAttribute("user", user);

        return "room";
    }

    @PostMapping("")
    public String create(String title) {
        //TODO Add secret mode logic
        long roomId  = lobby.createRoom(title);
        log.info("Created RoomId: {}", roomId);
        return "redirect:/room/" + roomId;
    }

    @DeleteMapping("")
    public String exit(HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        exitUserFromRoom(user);
        return "redirect:/lobby";
    }

    private void exitUserFromRoom(User user) {
        long currentRoomId = user.getEnteredRoomId();
        Room room = lobby.getRoom(currentRoomId);
        room.exit(user);
        if (room.isEmpty()) {
            lobby.delRoom(currentRoomId);
        }
    }
}
