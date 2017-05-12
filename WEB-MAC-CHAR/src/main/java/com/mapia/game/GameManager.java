package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.domain.User;

import java.util.List;
import java.util.Set;

public class GameManager {

    private GamePlayers players;

    public GameManager(Set<User> users) {
        this.players = new GamePlayers(users);
        RoleManager.assignRoleToPlayers(this.players);
    }

    public String findRoleNameByUserNickName(String userNickName) {
        return this.players.findRoleName(userNickName);
    }

    public List<Player> getPlayers() {
        return this.players.getPlayers();
    }
}
