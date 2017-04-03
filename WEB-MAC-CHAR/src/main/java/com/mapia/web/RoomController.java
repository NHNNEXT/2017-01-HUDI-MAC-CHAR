package com.mapia.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mapia.domain.Room;

@Controller
@RequestMapping("/rooms")
public class RoomController {
	private static final Logger log = LoggerFactory.getLogger(RoomController.class);
	
	private Map<Long, Room> rooms = new ConcurrentHashMap<>();
	private final AtomicLong roomId = new AtomicLong();
	
	@PostMapping("")
	public String create(String title){
		Room room = new Room(roomId.incrementAndGet(), title);
		log.info("{}. {} is MADE", room.getId(), room.getTitle());
		rooms.put(room.getId(), room);
		log.info("{}", rooms.toString());
		return "redirect:game_room.html?"+room.getId();
	}
}
