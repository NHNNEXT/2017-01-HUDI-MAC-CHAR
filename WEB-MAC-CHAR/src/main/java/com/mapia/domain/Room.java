package com.mapia.domain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mapia.game.GameResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mapia.game.GameManager;
import com.mapia.websocket.VoteMessage;

public class Room {
    private static final Logger log = LoggerFactory.getLogger(Room.class);

    private static final int CAPACITY = 8;
    private static final int MINIMUM_COUNT = 4;
    private Set<User> users = Collections.synchronizedSet(new LinkedHashSet(CAPACITY));
    private long id;
    private String title;
    private volatile int userCount;
    private boolean secretMode;
    private GameManager gameManager;

    public Room(long roomId, String title) {
        this.id = roomId;
        this.title = title;
    }

    public Set<User> getUsers() {
        return users;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getUserCount() {
        return getCountOfUserInRoom();
    }

    public boolean isSecretMode() {
        return secretMode;
    }

    public void setSecretMode() {
        this.secretMode = true;
    }

    public int getCountOfUserInRoom() {
        return users.size();
    }

    public boolean isFull() {
        return getCountOfUserInRoom() >= CAPACITY;
    }

    public boolean isEmpty() {
        return getCountOfUserInRoom() == 0;
    }

    @Override
    public String toString() {
        return "Room{" +
            "users=" + users +
            ", id=" + id +
            ", title='" + title + '\'' +
            ", userCount=" + userCount +
            '}';
    }

    public void enter(User user) {
        user.enterRoom(this.id);
        users.add(user);
    }

    public void exit(User user) {
        user.enterLobby();
        users.remove(user);
    }

    public boolean isAllReady() {
        if (this.users.size() < MINIMUM_COUNT) {
            log.debug("isAllReady::Not yet, count of users: {}", this.users.size());
            return false;
        }

        for (User user : this.users) {
            log.debug("isAllReady::user: {}, status: {}", user.getNickname(), user.getStatus());
            if (!user.isReady()) {
                return false;
            }
        }
        return true;
    }

    public User findByNickname(String nickName) {
        for (User user : this.users) {
            if (user.getNickname().equals(nickName)) {
                return user;
            }
        }
        return null;
    }

    public void createGameManager() {
        this.gameManager = new GameManager(this.users);
    }

    public String getUserRoleNameInGame(String userNickName) {
        return this.gameManager.findRoleNameByUserNickName(userNickName);
    }
    
    public GameResult returnVoteResult(VoteMessage voteMessage) {
        return this.gameManager.returnVoteResult(voteMessage);
    }
}
