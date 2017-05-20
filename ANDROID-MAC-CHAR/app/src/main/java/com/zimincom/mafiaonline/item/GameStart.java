package com.zimincom.mafiaonline.item;

public class GameStart {

    private String userName;

    public GameStart() {
    }

    public GameStart(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return String.format("userName:%s started!", userName);
    }
}
