package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 5. 17..
 */

public class Player extends User {

    String role;

    public Player(String nickname, String role) {
        super(nickname);
        this.role = role;
    }

}
