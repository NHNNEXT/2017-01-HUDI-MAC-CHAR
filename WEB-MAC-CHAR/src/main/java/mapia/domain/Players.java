package mapia.domain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Players {
	private Set<Player> players = null;

	private int capacity;
	
	public Players(int capacity) {
		this.capacity = capacity;
		this.players = Collections.synchronizedSet(new LinkedHashSet<>(capacity));
	}

	public void add(Player player) {
		if (players.size() >= this.capacity) {
			throw new IllegalStateException("정원을 초과해 더 이상 입장할 수 없습니다.");
		}
		
		player.enterRoom();
		players.add(player);
	}

	public Player findById(long id) {
		return this.players.stream()
			.filter(p -> p.matchId(id))
			.findFirst()
			.orElse(null);
	}
	
	public Player findBy(Player player) {
		return findById(player.id);
	}
	
	public Player ready(Player p) {
		Player player = findBy(p);
		if (player == null) {
			throw new IllegalStateException("{} 사용자는 게임 방에 존재하지 않는 사용자입니다.");
		}
		
		player.ready();
		return player;
	}

	public void startGame() {
		this.players.forEach(p -> p.startGame());
	}
}
