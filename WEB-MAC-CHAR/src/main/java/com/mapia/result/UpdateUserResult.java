package com.mapia.result;

import com.mapia.domain.User;
import com.mapia.result.RoomResult.Status;

public class UpdateUserResult implements Result {
    private Status status;
    private String msg;
    private User user;

    private UpdateUserResult(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private UpdateUserResult(Status status, User user) {
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

    public static UpdateUserResult ok(User user) {
        return new UpdateUserResult(Status.Ok, user);
    }
    
    public static UpdateUserResult invalidAccess() {
        return new UpdateUserResult(Status.InvalidAccess, "Invalid Access");
    }
    
    public static UpdateUserResult nicknameExist() {
    	return new UpdateUserResult(Status.NicknameExists, "Nickname Exists");
    }

}
