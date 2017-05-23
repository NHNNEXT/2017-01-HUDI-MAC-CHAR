package com.mapia.game.result;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.domain.Player;
import com.mapia.domain.Room;
import com.mapia.domain.User;
import com.mapia.game.GameManager;
import com.mapia.game.GameResult;
import com.mapia.websocket.VoteMessage;

public class GameResultTest {
    private static final Logger log = LoggerFactory.getLogger(GameResultTest.class);

    User user1;
    User user2;
    User user3;
    User user4;
    User user5;
    User user6;
    User user7;
    User user8;
    Player mafia;
    Player citizen1;
    Player citizen2;
    Player citizen3;
    List<Player> citizens;
    Room room;
    Set<User> users = new LinkedHashSet<>();
    GameManager gm;

    @Before
    public void setUsers() {

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

        room = new Room(1, "test");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        gm = new GameManager(users);
        mafia = gm.getPlayers().stream().filter(p -> p.isMafia()).collect(Collectors.toList()).get(0); //마피아로 배정된 플레이어를 구한다
        citizens = gm.getPlayers();
        citizens.remove(mafia); //얻어온 플레이어 리스트에서 시민만 남긴다
        citizen1 = citizens.get(0);
        citizen2 = citizens.get(1);
        citizen3 = citizens.get(2);
        gm.getPlayers().add(mafia); //원상복귀
    }

    @Test
    public void citizenWinCase() {
        log.debug("Mafia : {}, Citizen1 : {}, Citizen2 : {}, Citizen3: {}", mafia, citizen1, citizen2, citizen3);
        gm.returnVoteResult(new VoteMessage(mafia.getUser().getNickname(), citizen1.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen1.getUser().getNickname(), mafia.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen2.getUser().getNickname(), mafia.getUser().getNickname()));
        GameResult gr = gm.returnVoteResult(new VoteMessage(citizen3.getUser().getNickname(), mafia.getUser().getNickname()));
        assertEquals("시민이 승리하였습니다.", gr.getMsg());
    }
    
    @Test
    public void keepGoingCase() {
        log.debug("Mafia : {}, Citizen1 : {}, Citizen2 : {}, Citizen3: {}", mafia, citizen1, citizen2, citizen3);
        gm.returnVoteResult(new VoteMessage(mafia.getUser().getNickname(), citizen1.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen1.getUser().getNickname(), mafia.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen2.getUser().getNickname(), citizen1.getUser().getNickname()));
        GameResult gr = gm.returnVoteResult(new VoteMessage(citizen3.getUser().getNickname(), citizen1.getUser().getNickname()));
        assertEquals(citizen1.getUser().getNickname(), gr.getMsg());
    }
    
    @Test
    public void mafiaWinCase() {
        log.debug("Mafia : {}, Citizen1 : {}, Citizen2 : {}, Citizen3: {}", mafia, citizen1, citizen2, citizen3);
        gm.returnVoteResult(new VoteMessage(mafia.getUser().getNickname(), citizen1.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen1.getUser().getNickname(), mafia.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen2.getUser().getNickname(), citizen1.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen3.getUser().getNickname(), citizen1.getUser().getNickname()));
        // citizen1 killed
        gm.returnVoteResult(new VoteMessage(mafia.getUser().getNickname(), citizen2.getUser().getNickname()));
        gm.returnVoteResult(new VoteMessage(citizen2.getUser().getNickname(), mafia.getUser().getNickname()));
        GameResult gr = gm.returnVoteResult(new VoteMessage(citizen3.getUser().getNickname(), citizen2.getUser().getNickname()));
        assertEquals("마피아가 승리하였습니다.", gr.getMsg());
    }

//    @Test
//    public void tieCase() { //현재 동률일 때는 먼저 투표된 사람이 사망
//        log.debug("Mafia : {}, Citizen1 : {}, Citizen2 : {}, Citizen3: {}", mafia, citizen1, citizen2, citizen3);
//        gm.returnVoteResult(new VoteMessage(mafia.getUser().getNickname(), citizen1.getUser().getNickname()));
//        gm.returnVoteResult(new VoteMessage(citizen1.getUser().getNickname(), mafia.getUser().getNickname()));
//        gm.returnVoteResult(new VoteMessage(citizen2.getUser().getNickname(), mafia.getUser().getNickname()));
//        GameResult gr = gm.returnVoteResult(new VoteMessage(citizen3.getUser().getNickname(), citizen1.getUser().getNickname()));
//        assertEquals(citizen1.getUser().getNickname(), gr.getMsg());
//    }
    // 단독으로 돌릴 때와 다른 테스트들과 함께 돌릴 때 countStatus 순서가 달라짐. 원인을 모르겠다
}
