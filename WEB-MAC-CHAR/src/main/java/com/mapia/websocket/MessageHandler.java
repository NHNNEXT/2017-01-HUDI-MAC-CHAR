package com.mapia.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@RestController
public class MessageHandler {

    @MessageMapping("/hello")
    @SendTo("/topic/roomId")
    public ClientMessage broadcasting(ClientMessage message) throws Exception {
        return message;
    }
}
