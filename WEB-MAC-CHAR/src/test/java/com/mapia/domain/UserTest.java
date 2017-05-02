package com.mapia.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void isReady() {
		User user = new User();
		user.enterRoom(1);
		assertEquals(true, user.isReady());
	}

}
