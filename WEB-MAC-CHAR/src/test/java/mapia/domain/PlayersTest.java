package mapia.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayersTest {
	@Test
	public void add() throws Exception {
		Players players = new Players(1);
		Player expected = new Player(1L, "nickname");
		players.add(expected);
		assertEquals(expected, players.findById(1L));
	}
	
	@Test(expected = IllegalStateException.class)
	public void add_정원초과() throws Exception {
		Players players = new Players(1);
		players.add(new Player(1L, "nickname"));
		players.add(new Player(2L, "nickname2"));
	}
	
	@Test
	public void startGame() throws Exception {
		Players players = new Players(2);
		Player player1 = new Player(1L, "nickname");
		players.add(player1);
		players.ready(player1);
		
		Player player2 = new Player(2L, "nickname2");
		players.add(player2);
		players.ready(player2);

		players.startGame();
	}
}
