package com.mapia.result;

public interface Result {
    enum Status {
        Ok, EmailNotFound, InvalidPassword, EmailExists, NicknameExists;
    }
}
