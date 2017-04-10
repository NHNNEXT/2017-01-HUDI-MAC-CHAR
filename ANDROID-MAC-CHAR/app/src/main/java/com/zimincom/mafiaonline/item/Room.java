package com.zimincom.mafiaonline.item;

import java.util.Arrays;

/**
 * Created by Zimincom on 2017. 4. 10..
 */

public class Room {
    User[] users;
    String id;
    String title;
    int userCount;
    boolean secretMode;
    boolean empty;
    boolean full;


    public String getTitle() {
        return title;
    }

    public User[] getUsers() {
        return users;
    }

    public String getId() {
        return id;
    }

    public int getUserCount() {
        return userCount;
    }

    public boolean isSecretMode() {
        return secretMode;
    }

    public Room(User[] users, String id, String title, int userCount, boolean secretMode, boolean empty, boolean full) {
        this.users = users;
        this.id = id;
        this.title = title;
        this.userCount = userCount;
        this.secretMode = secretMode;
        this.empty = empty;
        this.full = full;
    }

    @Override
    public String toString() {
        return "Room{" +
                "users=" + Arrays.toString(users) +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", userCount=" + userCount +
                ", secretMode=" + secretMode +
                ", empty=" + empty +
                ", full=" + full +
                '}';
    }
}
