package com.mapia.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@RestController
public class MessageHandler {

    /*
     *  이 클래스는 Message-handling controller 이다.
     *
     *  - description
     *  해당 클래스는 @MessageMapping 어노테이션에 의해 "/hello"라는 api로 mapping 되어있다.
     *  만약에 클라이언트에서 "../hello"라는 api로 메세지를 보내면 `broadcasting()` 메소드가 호출된다.
     *  클라이언트로부터 오는 메세지는 `broadcasting()` 메소드의 파라미터와 binding 되어 있다.
     *
     *  return value는 @SendTo 어노테이션에 mapping 되어있는 api를 구독하고 있는 클라이언트들에게 브로드캐스팅 된다.
     */

    @MessageMapping("/hello")
    @SendTo("/topic/roomId")
    public Message broadcasting(ClientMessage message) throws Exception {
        return new Message(message.getContent());
    }
}
