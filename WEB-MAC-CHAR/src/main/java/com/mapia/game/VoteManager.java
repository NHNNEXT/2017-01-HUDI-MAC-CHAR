package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.websocket.VoteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class VoteManager {
    private static final Logger log = LoggerFactory.getLogger(VoteManager.class);

    private Map<Player, Player> voteStatus;
    private GamePlayers players;

    public VoteManager(GamePlayers players) {
        this.players = players;
        this.voteStatus = new HashMap<>(this.players.countOfPlayers());
    }

    public boolean handleVote(VoteMessage voteMessage) {
        Player playerVoting = this.players.getPlayer(voteMessage.getUserName());
        Player playerVoted = this.players.getPlayer(voteMessage.getTheVoted());
        voteStatus.put(playerVoting, playerVoted);
        if (voteStatus.size() != this.players.countOfPlayers()) {
            return false;
        }
        return true;
    }

    public String returnResult() {
        if (isFinished()) {
            return "finished";
        }
        return determineResult(countVote());
    }

    private boolean isFinished() {

        //경기 종료 여부 판정
        return false;
    }

    private Map<Player, Integer> countVote() {
        Map<Player, Integer> countStatus = new HashMap<>();
        voteStatus.keySet().forEach(player -> countStatus.put(player, 0));
        voteStatus.values().forEach(player -> countStatus.put(player, countStatus.get(player) + 1));
        log.debug("countStatus setting: {}", countStatus);
        return countStatus;
    }

    private String determineResult(Map<Player, Integer> countStatus) {
        Player resultPlayer = null;
        int base = 0;
        for (Map.Entry<Player, Integer> entry : countStatus.entrySet()) {
            //TODO 동률일 때 로직 추가
            if (entry.getValue() > base) {
                resultPlayer = entry.getKey();
                base = entry.getValue();
            }
        }
        log.debug("determineResult:resultPlayer: {}", resultPlayer);
        return resultPlayer.getUser().getNickname();
    }
}
