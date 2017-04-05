package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 4. 3..
 */

public class User {

    String nickname;
    String email;
    String password;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String nickname, String email, String password){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

}
