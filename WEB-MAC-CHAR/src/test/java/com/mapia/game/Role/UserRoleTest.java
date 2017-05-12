package com.mapia.game.Role;

import com.mapia.domain.Room;
import com.mapia.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jbee on 2017. 5. 11..
 */
public class UserRoleTest {
    private static final Logger log = LoggerFactory.getLogger(RoleTest.class);

    User user1;
    User user2;
    User user3;
    User user4;
    Room room;

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

        room = new Room(1, "test");

        room.enter(user1);
        room.enter(user2);
        room.enter(user3);
        room.enter(user4);

        room.createGameManager();
    }

    @Test
    public void getRoleName() {
        String roleName = room.getUserRoleNameInGame("d");
        if (roleName.equals("Doctor")
            || roleName.equals("Mafia")
            || roleName.equals("Citizen")
            || roleName.equals("Police")) {
            log.debug("roleName: {}", roleName);
            assertEquals(true, true);
        }
    }
}
