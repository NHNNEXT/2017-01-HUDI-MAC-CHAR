package com.mapia.result;

import com.mapia.domain.User;

public class LoginResult implements Result {
    private Status status;
    private String msg;
    private User user;

    private LoginResult(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private LoginResult(Status status, User user) {
        this.status = status;
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public User getUser() {
        return user;
    }

    public static LoginResult ok(User user) {
        return new LoginResult(Status.Ok, user);
    }

    public static LoginResult emailNotFound(String msg) {
        return new LoginResult(Status.EmailNotFound, msg);
    }

    public static LoginResult invalidPassword(String msg) {
        return new LoginResult(Status.InvalidPassword, msg);
    }
}
