package com.mapia.websocket;

/**
 * Created by Jbee on 2017. 3. 28..
 */
public class Message {
    private String content;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
