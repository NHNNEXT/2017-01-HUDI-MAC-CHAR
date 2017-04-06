package com.mapia.web;

import com.mapia.domain.Room;
import com.mapia.domain.Rooms;
import com.mapia.domain.User;
import com.mapia.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by Jbee on 2017. 4. 4..
 */
@Controller
@RequestMapping("/game")
public class GameRoomController {
    private static final Logger log = LoggerFactory.getLogger(GameRoomController.class);

    @Autowired
    private Rooms rooms;

    @GetMapping("/{id}")
    public String enter(@PathVariable long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/login";
        }
        User user = HttpSessionUtils.getUserFromSession(session);
        log.info("들어갑니다~ user: {}", user);
        if (!rooms.isExistRoom(id)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            if (!user.isLobby()) {
                exitUserFromRoom(user);
                log.info("게임방에서 존재하지 않은 방으로의 잘못된 접근에 대한 처리, user: {}", user);
            }
            log.info("로비에서 존재하지 않은 방으로의 잘못된 접근에 대한 처리, user: {}", user);
            return "redirect:/";
        }

        Room room = rooms.getRoom(id);
        if (!user.isAbleToEnter(id)) {
            session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
            exitUserFromRoom(user);
            log.info("로비든 게임방이든에서 잘못된 접근에 대한 처리, user: {}", user);
            return "redirect:/";
        }
        log.info("정상적인 user: {}", user);
        if (room.isFull()) {
            return "redirect:/rooms";
        }

        if (room.isSecretMode()) {
            //TODO Add secret mode logic
        }

        user.enterRoom(room);
        log.info("GameRoom entered, room: {}", room);
        model.addAttribute("room", room);
        model.addAttribute("nickname", user.getNickname());

        return "game_room";
    }

    @DeleteMapping("")
    public String exit(HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        exitUserFromRoom(user);
        return "redirect:/rooms";
    }

    private void exitUserFromRoom(User user) {
        long currentRoomId = user.getBelongToRoomId();
        Room room = rooms.getRoom(currentRoomId);
        user.exitRoom(room);
        if (room.isEmpty()) {
            rooms.delRoom(currentRoomId);
        }
    }
}
