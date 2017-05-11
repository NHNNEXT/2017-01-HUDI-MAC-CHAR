package com.mapia.game.Role;

import com.mapia.domain.Player;
import com.mapia.domain.Room;
import com.mapia.domain.User;
import com.mapia.game.GameManager;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jbee on 2017. 5. 11..
 */
public class RoleTest {
    private static final Logger log = LoggerFactory.getLogger(RoleTest.class);

    User user1;
    User user2;
    User user3;
    User user4;
    User user5;
    User user6;
    User user7;
    User user8;
    Room room;
    Set<User> users = new LinkedHashSet<>();

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
    }

    public void countRoles(List<Player> players,
                           int mafia,
                           int police,
                           int citizen,
                           int doctor) {
        int countOfCitizen = 0;
        int countOfMafia = 0;
        int countOfPolice = 0;
        int countOfDoctor = 0;

        for (Player player : players) {
            if (player.getRoleName().equals("Citizen")) {
                countOfCitizen++;
            }
            if (player.getRoleName().equals("Mafia")) {
                countOfMafia++;
            }
            if (player.getRoleName().equals("Police")) {
                countOfPolice++;
            }
            if (player.getRoleName().equals("Doctor")) {
                countOfDoctor++;
            }
        }

        assertEquals(countOfCitizen, citizen);
        assertEquals(countOfMafia, mafia);
        assertEquals(countOfPolice, police);
        assertEquals(countOfDoctor, doctor);
    }

    @Test
    public void isCorrectShuffleWhenFour() {
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        countRoles(new GameManager(users).getPlayers(), 1, 1, 1, 1);
    }

    @Test
    public void isCorrectShuffleWhenFive() {
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        countRoles(new GameManager(users).getPlayers(), 1, 1, 2, 1);
    }

    @Test
    public void isCorrectShuffleWhenSix() {
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        countRoles(new GameManager(users).getPlayers(), 2, 1, 2, 1);
    }

    @Test
    public void isCorrectShuffleWhenSeven() {
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        countRoles(new GameManager(users).getPlayers(), 2, 1, 3, 1);
    }

    @Test
    public void isCorrectShuffleWhenEight() {
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        countRoles(new GameManager(users).getPlayers(), 2, 1, 4, 1);
    }
}
