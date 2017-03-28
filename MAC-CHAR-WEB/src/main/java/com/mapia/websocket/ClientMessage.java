package com.mapia.websocket;

/**
 * Created by Jbee on 2017. 3. 28..
 */
public class ClientMessage {
    private String content;

    public ClientMessage() {}

    public ClientMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
