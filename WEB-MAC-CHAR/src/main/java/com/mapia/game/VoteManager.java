package com.mapia.game;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.domain.Player;
import com.mapia.websocket.VoteMessage;

public class VoteManager {
    private static final Logger log = LoggerFactory.getLogger(VoteManager.class);

    private Map<Player, Player> voteStatus;
    private GamePlayers players;

    public VoteManager(GamePlayers players) {
        this.players = players;
        this.voteStatus = new HashMap<>(this.players.countOfPlayers());
        //TODO Below code is TEST CODE, DELETE or COMMENT this code before commit.
//        this.voteStatus.put(this.players.getPlayer("testUser1"), this.players.getPlayer("testUser1"));
//        this.voteStatus.put(this.players.getPlayer("testUser2"), this.players.getPlayer("testUser2"));
//        this.voteStatus.put(this.players.getPlayer("testUser3"), this.players.getPlayer("testUser3"));
        // test room 에 미리 들어가 있던 세명의 testUser 는 각각 자신을 vote 한다.
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
        voteStatus.values().forEach(player -> {
            if (player != null) { //기권표를 걸러낸다.
                countStatus.put(player, countStatus.get(player) + 1);
            }
        });
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
        this.players.removeDeadPlayer(resultPlayer); //투표의 결과로 사망시 players에서 제외
        voteStatus.clear(); //투표가 종료된 뒤 voteStatus 초기화
        return resultPlayer.getUser().getNickname();
    }
}
