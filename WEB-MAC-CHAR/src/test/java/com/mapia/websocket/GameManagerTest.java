package com.mapia.websocket;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.domain.GameManager;
import com.mapia.domain.Player;
import com.mapia.domain.User;

import junit.framework.Assert;

public class GameManagerTest {
	private static final Logger log = LoggerFactory.getLogger(GameManagerTest.class);
	Set<User> users = new HashSet<User>();

	@Test
	public void assignRole() {
		users.add(new User());
		users.add(new User());
		users.add(new User());
		users.add(new User());
		users.add(new User());
		GameManager gm = new GameManager(users);
	}
}
