package com.mapia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mapia.utils.PasswordEncryptUtils;

public class User {
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

    public User() {
    }

    public User(long id, String email, String nickname) {
    	this.id = id;
    	this.email = email;
    	this.nickname = nickname;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = PasswordEncryptUtils.getEncSHA256(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void gameStart() {
    	this.status = Status.IN_GAME;
    }
    
    public void ready() {
    	this.status = Status.READY;
    }

    public long getEnteredRoomId() {
        return this.enteredRoomId;
    }

    public void setEnteredRoomId(long enteredRoomId) {
        this.enteredRoomId = enteredRoomId;
    }

    public boolean matchPassword(User user) {
        try {
            return this.password.equals(PasswordEncryptUtils.getEncSHA256(user.password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAbleToEnter(long id) {
        return this.status == Status.LOBBY || enteredRoomId == id;
    }

    @JsonIgnore
    public boolean isLobby() {
        return this.status.isLobby();
    }

    public boolean isSameUser(String nickname) {
        return this.nickname.equals(nickname);
    }

    public void enterLobby() {
        this.enteredRoomId = LOBBY_ID;
        this.status = Status.LOBBY;
    }

    public void enterRoom(long id) {
        this.enteredRoomId = id;
        this.status = Status.NOT_READY;
    }

    @Override
    public String toString() {
        return "User[nickname=" + nickname +
            ", Status=" + status +
            ", enteredRoomId=" + enteredRoomId;
    }

    public boolean isReady() {
        return getStatus() == Status.READY;
    }

    public void toggleReady() {
        status = status == Status.READY ? Status.NOT_READY : Status.READY;
    }

}
