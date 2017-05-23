package com.mapia.game;

import com.mapia.domain.Player;
import com.mapia.domain.User;
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
        //TODO Below code is TEST CODE, DELETE or COMMENT this code before commit.
//        Player p1 = new Player(new User());
//        Player p2 = new Player(new User());
//        Player p3 = new Player(new User());
//        this.voteStatus.put(p1, p1);
//        this.voteStatus.put(p2, p2);
//        this.voteStatus.put(p3, p3);
//        here.
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

    public GameResult returnGameResult() {
        String killedUserNickName = determineResult(countVote());
        GameResultType gameResultType = this.players.judgementPlayersCount();
        switch (gameResultType) {
            case MAFIA_WIN:
                return new GameResult(GameResultType.MAFIA_WIN);
            case CITIZEN_WIN:
                return new GameResult(GameResultType.CITIZEN_WIN);
            case KEEP_GOING:
                return new GameResult(killedUserNickName);
            default:
                return null;
        }
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
            //현재는 둘 중 먼저 투표된 사람이 killed
            if (entry.getValue() > base) {
                resultPlayer = entry.getKey();
                base = entry.getValue();
            }
        }
        log.debug("determineResult:resultPlayer: {}", resultPlayer);
        resultPlayer.kill();
        return resultPlayer.getUser().getNickname();
    }
}
