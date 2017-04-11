package mapia.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	@Test
	public void player_from_lobby_to_ingame() throws Exception {
		Player player = new Player(1L, "nickname");
		assertEquals(new Player(1L, "nickname", Player.Status.LOBBY), player);
		player.enterRoom();
		assertEquals(new Player(1L, "nickname", Player.Status.NOT_READY), player);
		player.ready();
		assertEquals(new Player(1L, "nickname", Player.Status.READY), player);
		player.startGame();
		assertEquals(new Player(1L, "nickname", Player.Status.IN_GAME), player);
	}
}
