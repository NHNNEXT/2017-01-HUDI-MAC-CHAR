package com.mapia.websocket;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapia.domain.Lobby;

public class ReadySignal {
    private String userName;
    private boolean startTimer;

    @Autowired
    private Lobby lobby;

    public ReadySignal() {
    }

    public ReadySignal(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStartTimer(boolean startTimer) {
        this.startTimer = startTimer;
    }

    public void startTimer() {
        this.startTimer = true;
    }

    public void stopTimer() {
        this.startTimer = false;
    }

    public boolean isStartTimer() {
        return startTimer;
    }

    @Override
    public String toString() {
        return String.format("userName:%s, isStart:%s", userName, startTimer);
    }
}
