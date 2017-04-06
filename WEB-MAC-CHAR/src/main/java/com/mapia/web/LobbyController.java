package com.mapia.web;

import com.mapia.domain.Lobby;
import com.mapia.domain.User;
import com.mapia.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/lobby")
public class LobbyController {
	private static final Logger log = LoggerFactory.getLogger(LobbyController.class);

	@Autowired
	private Lobby lobby;

	@GetMapping("")
	public String waitingRoomPage(HttpSession session, Model model) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/login";
		}

		User user = HttpSessionUtils.getUserFromSession(session);
		user.enterLobby();
		model.addAttribute("lobby", lobby);
		model.addAttribute("user", user);
		return "lobby";
	}
}
