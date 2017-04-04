package com.mapia.web;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.mapia.domain.User;
import com.mapia.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mapia.domain.Room;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/rooms")
public class RoomController {
	private static final Logger log = LoggerFactory.getLogger(RoomController.class);
	
	private volatile Map<Long, Room> rooms = new ConcurrentHashMap<>();
	private final AtomicLong roomId = new AtomicLong();

	@GetMapping("")
	public String waitingRoomPage(HttpSession session, Model model) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/login";
		}
		model.addAttribute("rooms", new ArrayList<>(rooms.values()));
		return "rooms";
	}

	@GetMapping("/{id}")
	public String enter(@PathVariable long id, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/login";
		}
		User user = HttpSessionUtils.getUserFromSession(session);
        Room room = rooms.get(id);
        if (!user.isEntered()) {
            room.outRoom(user);
            return "redirect:/rooms";
        }
        if (!room.isFull()) {
            //Insert to message
            return "redirect:/rooms";
        }

        if (room.isSecretMode()) {
            //TODO Add secret mode logic
        }
        room.enterRoom(user);
        return "game_room";
	}
	
	@PostMapping("")
	public String create(String title) {
		Room room = new Room(roomId.incrementAndGet(), title);
		log.info("Created RoomId: {}, title: {}", room.getId(), room.getTitle());
		long roomId = room.getId();
		rooms.put(roomId, room);
		return "redirect:/rooms/" + roomId;
	}
}
