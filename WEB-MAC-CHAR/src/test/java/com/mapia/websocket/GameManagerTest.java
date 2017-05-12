package com.mapia.websocket;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.game.GameManager;
import com.mapia.domain.User;

public class GameManagerTest {
    private static final Logger log = LoggerFactory.getLogger(GameManagerTest.class);
    Set<User> users = new HashSet<User>();

    @Test
    public void assignRole() {
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        GameManager gm = new GameManager(users);
    }
}
