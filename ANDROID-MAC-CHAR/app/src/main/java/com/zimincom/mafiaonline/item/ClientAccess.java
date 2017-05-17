package com.zimincom.mafiaonline.item;

import java.util.ArrayList;

public class ClientAccess {
    private String userName;
    private String access;
    private ArrayList<User> users;

    public ClientAccess() {
    }

    public ClientAccess(String userName, String access) {
        this.userName = userName;
        this.access = access;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return String.format("userName:%s, access:%s", userName, access);
    }
}
