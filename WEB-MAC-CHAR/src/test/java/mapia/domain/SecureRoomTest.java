package mapia.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecureRoomTest {
	@Test
	public void enter() throws Exception {
		SecureRoom room = new SecureRoom(1L, "비밀의 방", 3, "password");
		Player player = new Player(1L, "nickname", "password");
		room.enter(player);
		assertNotNull(room.findPlayer(player));
	}
	
	@Test (expected = IllegalStateException.class)
	public void enter_dont_match_password() throws Exception {
		SecureRoom room = new SecureRoom(1L, "비밀의 방", 3, "password");
		Player player = new Player(1L, "nickname", "password2");
		room.enter(player);
	}
}
