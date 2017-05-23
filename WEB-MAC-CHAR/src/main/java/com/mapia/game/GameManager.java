package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.domain.User;

import java.util.List;
import java.util.Set;

import com.mapia.websocket.VoteMessage;

public class GameManager {

    private GamePlayers players;
    private VoteManager voteManager;

    public GameManager(Set<User> users) {
        this.players = new GamePlayers(users);
        RoleManager.assignRoleToPlayers(this.players);
        this.voteManager = new VoteManager(players);
    }

    public String findRoleNameByUserNickName(String userNickName) {
        return this.players.findRoleName(userNickName);
    }

    public GameResult returnVoteResult(VoteMessage voteMessage) {
        if (!voteManager.handleVote(voteMessage)) {
            return GameResult.VotingStatus();
        }
        return voteManager.returnGameResult();
    }

    public List<Player> getPlayers() {
        return this.players.getPlayers();
    }

    public GamePlayers getGamePlayers() {
        return this.players;
    }
}
