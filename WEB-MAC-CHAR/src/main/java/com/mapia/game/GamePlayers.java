package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.domain.User;
import com.mapia.domain.role.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GamePlayers {
    private static final Logger log = LoggerFactory.getLogger(GamePlayers.class);

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
        return "undefined";
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getPlayer(String userName) {
        for (Player player : this.players) {
            if (player.isSameNickName(userName)) {
                return player;
            }
        }
        return null;
    }

    public GameResultType judgementPlayersCount() {
        int mafiaCount = (int) this.players.stream().filter(player -> player.isMafia()).count();
        int citizenCount = (this.players.size() - mafiaCount);
        log.debug("judgementPlayersCount:: MafiaCount: {}, CitizenCount: {}", mafiaCount, citizenCount);
        if (mafiaCount == 0) {
            return GameResultType.CITIZEN_WIN;
        } else if (citizenCount <= mafiaCount) {
            return GameResultType.MAFIA_WIN;
        }
        return GameResultType.KEEP_GOING;
    }
}
