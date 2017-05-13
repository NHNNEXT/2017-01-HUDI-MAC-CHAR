package com.mapia.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoomTest {

	@Test
	public void isNotAllReady() {
		Room room = new Room(1, "testRoom");
		
		room.enter(new User());
		room.enter(new User());
		room.enter(new User());
		assertEquals(false, room.isAllReady());
	}

	@Test
	public void allReady() {
		Room room = new Room(1, "testRoom");

		room.enter(new User());
		room.enter(new User());
		room.enter(new User());
		room.enter(new User());
		assertEquals(true, room.isAllReady());
	}
	
	@Test
	public void getHowMany() {
		Room room = new Room(1, "testRoom");
		
		room.enter(new User());
		room.enter(new User());
		room.enter(new User());
		
		assertEquals(3, room.getUserCount());
	}

	@Test
    public void getUserNickName() {
	    Room room = new Room(1, "testRoom");
        User user = new User();
        user.setId(1);
        user.setNickname("test");
	    room.enter(user);
	    assertEquals(user, room.findByNickname("test"));
    }
}
