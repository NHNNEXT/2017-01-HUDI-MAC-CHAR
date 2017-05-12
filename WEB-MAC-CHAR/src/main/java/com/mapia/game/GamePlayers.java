package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.domain.User;
import com.mapia.domain.role.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GamePlayers {
    private List<Player> players;

    public GamePlayers(Set<User> users) {
        this.players = new ArrayList<>(users.size());
        for (User user : users) {
            this.players.add(new Player(user));
        }
    }

    public int countOfPlayers() {
        return this.players.size();
    }

    public void setRole(List<Role> roles) {
        for (int i = 0; i < countOfPlayers(); i++) {
            this.players.get(i).setRole(roles.get(i));
        }
    }

    public String findRoleName(String userNickName) {
        for (Player player : this.players) {
            if (player.isSameNickName(userNickName)) {
                return player.getRoleName();
            }
        }
        throw new RuntimeException("Cannot find user");
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
