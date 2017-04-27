package com.mapia.websocket;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.domain.GameManager;
import com.mapia.domain.Player;

public class GameManagerTest {
	private static final Logger log = LoggerFactory.getLogger(GameManagerTest.class);
	GameManager gm = new GameManager();

	@Test
	public void assignRole() {
		List<Player> players = new ArrayList<>();
		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
		gm.setPlayers(players);
		gm.assignRole(players);
		for(int i = 0 ; i < players.size() ; i++) {
			log.debug("{}", players.get(i).getRole().getRoleName());
		}
	}
}
