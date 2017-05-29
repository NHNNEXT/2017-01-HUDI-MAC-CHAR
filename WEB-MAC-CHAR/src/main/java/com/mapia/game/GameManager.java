package com.mapia.game;

import com.mapia.domain.User;
import com.mapia.websocket.VoteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class GameManager {
    private static final Logger log = LoggerFactory.getLogger(GameManager.class);

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
        log.debug("returnVoteResult: {}", voteMessage);
        if (!voteManager.handleVote(voteMessage)) {
            return GameResult.votingStatus();
        }
        return voteManager.returnGameResult(voteMessage.getStage());
    }
}
