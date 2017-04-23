package com.zimincom.mafiaonline.item;

import java.io.Serializable;

/**
 * Created by Zimincom on 2017. 4. 3..
 */

public class User implements Serializable{

    public enum Status {
        LOBBY, READY, NOT_READY, IN_GAME;

        public boolean isLobby() {
            return this == Status.LOBBY;
        }
    }

    private long id;
    private String email;
    private String password;
    private String nickname;
    private Status status;
    private long enteredRoomId;
    private long LOBBY_ID = 0;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String nickname, String email, String password){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }


    public String getNickName() {
        return nickname;
    }
}
