package com.zimincom.mafiaonline.item;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2017. 4. 3..
 */

public class ResponseItem {

    public String status;
    String msg;
    ArrayList<Room> rooms;
    User user;


    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public User getUser(){
        return user;
    }
    @Override
    public String toString() {
        return "ResponseItem{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", rooms=" + rooms +
                ", rooms=" + rooms +
                ", user=" + user.getNickName() +
                '}';
    }
}
