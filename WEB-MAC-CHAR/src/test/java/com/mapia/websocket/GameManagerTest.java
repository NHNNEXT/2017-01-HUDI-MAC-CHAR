package com.mapia.websocket;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.domain.GameManager;
import com.mapia.domain.Player;

import junit.framework.Assert;

public class GameManagerTest {
	private static final Logger log = LoggerFactory.getLogger(GameManagerTest.class);
	GameManager gm = new GameManager();

	@Test
	public void assignRole() {
		gm.addPlayer(new Player());
		gm.addPlayer(new Player());
		gm.addPlayer(new Player());
		gm.addPlayer(new Player());
		gm.addPlayer(new Player());
		gm.assignRole();
	}
	
	@Test
	public void a() {
		Assert.assertEquals(true, "asdf" == "asdf");
	}
}
