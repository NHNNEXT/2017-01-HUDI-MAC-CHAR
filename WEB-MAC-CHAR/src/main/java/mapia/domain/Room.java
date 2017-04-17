package mapia.domain;

public class Room {
	private Players players;
	
	private long id;
	private String title;

	public Room(long id, String title, int capacity) {
		this.id = id;
		this.title = title;
		this.players = new Players(capacity);
	}

	public void enter(Player player) {
		players.add(player);
	}

	public Player findPlayer(Player player) {
		return players.findBy(player);
	}

	public Player ready(Player p) {
		return players.ready(p);
	}

	public void startGame() {
		players.startGame();
	}
}
