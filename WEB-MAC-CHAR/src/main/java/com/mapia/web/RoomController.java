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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/rooms")
public class RoomController {
	private static final Logger log = LoggerFactory.getLogger(RoomController.class);

	@Autowired
	private Rooms rooms;

	@GetMapping("")
	public String waitingRoomPage(HttpSession session, Model model) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/login";
		}

		User user = HttpSessionUtils.getUserFromSession(session);
		user.enterLobby();
		model.addAttribute("rooms", rooms.getRooms());
		model.addAttribute("user", user);
		return "rooms";
	}

	@PostMapping("")
	public String create(String title) {
        //TODO Add secret mode logic
		long roomId  = rooms.createRoom(title);
		log.info("Created RoomId: {}", roomId);
		return "redirect:/game/" + roomId;
	}
}
