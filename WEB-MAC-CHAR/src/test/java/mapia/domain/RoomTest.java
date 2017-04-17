package mapia.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoomTest {
	private Room room;

	@Before
	public void setup() {
		room = new Room(1L, "재밌는 방", 8);
	}

	@Test
	public void enter() throws Exception {
		Player player = new Player(1L, "nickname");
		room.enter(player);
		assertNotNull(room.findPlayer(player));
	}

	@Test(expected = IllegalStateException.class)
	public void enter_정원초과() throws Exception {
		room = new Room(1L, "재밌는 방", 1);

		Player user = new Player(1L, "nickname");
		room.enter(user);
		Player user2 = new Player(2L, "nickname2");
		room.enter(user2);
	}

	@Test
	public void ready() throws Exception {
		Player user = new Player(1L, "nickname");
		room.enter(user);
		Player player = room.ready(user);
	}

	@Test(expected = IllegalStateException.class)
	public void ready_게임_참여하지_않은_사용자() throws Exception {
		Player user = new Player(1L, "nickname");
		room.ready(user);
	}

	@Test
	public void startGame() throws Exception {
		Player user1 = createReadyUser(1L, "nickname1");
		Player user2 = createReadyUser(2L, "nickname2");

		room.startGame();
	}

	private Player createReadyUser(long id, String nickname) {
		Player user = new Player(id, nickname);
		room.enter(user);
		room.ready(user);
		return user;
	}
}
