package com.mapia.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class RoomTest {

	@Test
	public void allReady() {
		Room room = new Room(1, "testRoom");
		
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
	
}
