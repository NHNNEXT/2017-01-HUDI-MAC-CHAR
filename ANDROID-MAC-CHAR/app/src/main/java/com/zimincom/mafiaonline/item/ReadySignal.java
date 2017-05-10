package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 5. 7..
 */

public class ReadySignal {

    private String userName;
    private boolean startTimer;

    Lobby lobby;

    public ReadySignal() {}

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

    public boolean isStartTimer() {
        return startTimer;
    }

}
