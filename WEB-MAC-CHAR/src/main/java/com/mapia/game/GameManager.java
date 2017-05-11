package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameManager {
	private List<Player> players = new ArrayList<>();
	
	public GameManager(Set<User> users) {
		for (User user : users) {
            this.players.add(new Player(user));
		}
        new RoleManager().assignRoleToPlayers(players);
	}

	public String findRoleNameByUserNickName(String userNickName) {
		for (Player player : this.players) {
		    if (player.isSameNickName(userNickName)) {
		        return player.getRoleName();
            }
		}
		return null;
	}

	public List<Player> getPlayers() {
	    return this.players;
    }
}
