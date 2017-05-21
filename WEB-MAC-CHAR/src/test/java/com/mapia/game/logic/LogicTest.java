package com.mapia.game.logic;

import com.mapia.domain.User;
import com.mapia.game.GameManager;
import com.mapia.game.GamePlayers;
import com.mapia.game.VoteManager;
import com.mapia.websocket.VoteMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jbee on 2017. 5. 17..
 */
public class LogicTest {

    User user1;
    User user2;
    User user3;
    User user4;
    User user5;
    User user6;
    User user7;
    User user8;
    Set<User> users = new LinkedHashSet<>();
    GameManager gameManager;
    VoteManager voteManager;
    VoteMessage msg1;
    VoteMessage msg2;
    VoteMessage msg3;
    VoteMessage msg4;

    @Before
    public void setUp() {
        user1 = new User();
        user1.setId(1);
        user1.setNickname("a");

        user2 = new User();
        user2.setId(2);
        user2.setNickname("b");

        user3 = new User();
        user3.setId(3);
        user3.setNickname("c");

        user4 = new User();
        user4.setId(4);
        user4.setNickname("d");

        user5 = new User();
        user5.setId(5);
        user5.setNickname("e");

        user6 = new User();
        user6.setId(6);
        user6.setNickname("f");

        user7 = new User();
        user7.setId(7);
        user7.setNickname("g");

        user8 = new User();
        user8.setId(8);
        user8.setNickname("h");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        gameManager = new GameManager(users);
        GamePlayers players = new GamePlayers(users);

        voteManager = new VoteManager(players);
        msg1 = new VoteMessage();
        msg2 = new VoteMessage();
        msg3 = new VoteMessage();
        msg4 = new VoteMessage();
    }

    @Test
    public void 네명일때_handleVote_TEST() {
        msg1.setUserName("a");
        msg1.setTheVoted("c");
        msg2.setUserName("b");
        msg2.setTheVoted("d");
        msg3.setUserName("c");
        msg3.setTheVoted("d");
        msg4.setUserName("d");
        msg4.setTheVoted("d");
        assertEquals(false, voteManager.handleVote(msg1));
        assertEquals(false, voteManager.handleVote(msg2));
        assertEquals(false, voteManager.handleVote(msg3));
        assertEquals(true, voteManager.handleVote(msg4));
    }

    @Test
    public void 네명일때_determineResult() {
        msg1.setUserName("a");
        msg1.setTheVoted("c");
        msg2.setUserName("b");
        msg2.setTheVoted("d");
        msg3.setUserName("c");
        msg3.setTheVoted("d");
        msg4.setUserName("d");
        msg4.setTheVoted("d");
        voteManager.handleVote(msg1);
        voteManager.handleVote(msg2);
        voteManager.handleVote(msg3);
        voteManager.handleVote(msg4);
        assertEquals("d", voteManager.returnResult());
    }

    @Test
    public void 네명일때_returnVoteResult_TEST() {
        msg1.setUserName("a");
        msg1.setTheVoted("c");
        msg2.setUserName("b");
        msg2.setTheVoted("d");
        msg3.setUserName("c");
        msg3.setTheVoted("d");
        msg4.setUserName("d");
        msg4.setTheVoted("d");
        assertEquals("투표가 진행중입니다", gameManager.returnVoteResult(msg1));
        assertEquals("투표가 진행중입니다", gameManager.returnVoteResult(msg2));
        assertEquals("투표가 진행중입니다", gameManager.returnVoteResult(msg3));
        assertEquals("d", gameManager.returnVoteResult(msg4));
    }

    @Test
    public void lamda() {
        Map<String, Integer> temp = new HashMap<>();
        temp.put("1", 1);
        temp.put("2", 2);
        temp.put("3", 3);
        temp.put("4", 4);
        temp.put("5", 5);
        assertEquals(5, temp.values().stream().max(Integer::compareTo).get().intValue());
    }
}
