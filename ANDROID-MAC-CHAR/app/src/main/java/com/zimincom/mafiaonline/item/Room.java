package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 4. 10..
 */

public class Room {
    User[] users;
    int id;
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

    public int getId() {
        return id;
    }

    public int getUserCount() {
        return userCount;
    }

    public boolean isSecretMode() {
        return secretMode;
    }

    public Room(User[] users, int id, String title, int userCount, boolean secretMode, boolean empty, boolean full) {
        this.users = users;
        this.id = id;
        this.title = title;
        this.userCount = userCount;
        this.secretMode = secretMode;
        this.empty = empty;
        this.full = full;
    }

}
