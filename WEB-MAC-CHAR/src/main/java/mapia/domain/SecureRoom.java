package mapia.domain;

public class SecureRoom extends Room {
	private String password;

	public SecureRoom(long id, String title, int capacity, String password) {
		super(id, title, capacity);
		assert password != null;
		this.password = password;
	}
	
	@Override
	public void enter(Player player) {
		if (!player.matchPassword(password)) {
			throw new IllegalStateException("비밀번호가 일치하지 않아 입장할 수 없습니다.");
		}
		
		super.enter(player);
	}
}
