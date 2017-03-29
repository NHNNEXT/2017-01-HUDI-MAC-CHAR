package com.mapia.web;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mapia.domain.Room;

@Controller
@RequestMapping("/rooms")
public class RoomController {
	
	private static ConcurrentHashMap<Long, Room> rooms = new ConcurrentHashMap<>();
	private static Long roomId = (long) 0;
	
	@PostMapping("")
	public String create(String title){
		Room room = new Room(roomId++, title);
		System.out.println(room.getId()+". "+room.getTitle()+" MADE");
		rooms.put(room.getId(), room);
		System.out.println(rooms.toString());
		return "redirect:game_room.html?"+room.getId();
	}
}
