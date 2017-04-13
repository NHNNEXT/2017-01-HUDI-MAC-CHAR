package com.zimincom.mafiaonline.item;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2017. 4. 3..
 */

public class ResponseItem {

    public String status;
    String msg;
    String nickname;
    ArrayList<Room> rooms;
    User user;


    public ArrayList<Room> getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        return "ResponseItem{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", rooms=" + rooms +
                ", user=" + user.nickname +
                '}';
    }
}
