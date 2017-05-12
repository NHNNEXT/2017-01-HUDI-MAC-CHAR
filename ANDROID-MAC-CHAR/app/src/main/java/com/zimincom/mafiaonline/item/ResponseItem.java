package com.zimincom.mafiaonline.item;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2017. 4. 3..
 */

public class ResponseItem {


    String msg;
    ArrayList<Room> rooms;
    User user;
    String roomId;
    private String status;

    public boolean isOk() {
        return status.equals("Ok");
    }

    public boolean isEmailNotFound() {
        return status.equals("EmailNotFound");
    }

    public boolean isPasswordInvaild() {
        return status.equals("InvaildPassword");
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public String getRoomId() {
        return roomId;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ResponseItem{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", rooms=" + rooms +
                ", roomId='" + roomId + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
