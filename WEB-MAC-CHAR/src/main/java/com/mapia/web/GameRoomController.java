package com.mapia.web;

import com.mapia.domain.Room;
import com.mapia.domain.User;
import com.mapia.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/{id}")
    public String enter(@PathVariable long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/login";
        }
        Room room = (Room) session.getAttribute("enteredRoom");
        log.info("GameRoom entered, room: {}", room);
        model.addAttribute("room", room);
        session.setAttribute(getRoomIdKey(id), id);

        User user = HttpSessionUtils.getUserFromSession(session);
        model.addAttribute("nickname", user.getNickname());

        return "game_room";
    }

    private String getRoomIdKey(long id) {
        return "roomId:" +id;
    }
}
