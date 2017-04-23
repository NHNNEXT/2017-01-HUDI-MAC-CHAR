package com.zimincom.mafiaonline.item;

/**
 * Created by Zimincom on 2017. 3. 30..
 */

public class MessageItem {

    public String userName;
    public String content;

    public MessageItem(String content) {
        this.content = content;
    }

    public MessageItem(String userName, String content){
        this.userName = userName;
        this.content = content;
    }
}
