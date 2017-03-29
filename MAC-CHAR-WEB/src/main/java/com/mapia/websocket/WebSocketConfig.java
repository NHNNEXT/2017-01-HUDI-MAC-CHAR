package com.mapia.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by Jbee on 2017. 3. 28..
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /*
     *  이 클래스는 Message broker 를 설정한다
     *
     *  enableSimpleBroker() 메소드는 메모리 기반 메세지 브로커가
     *  해당 api를 구독하고 있는 클라이언트에게 메세지를 전달한다.
     *
     *  setApplicationDestinationPrefixes 메소드는
     *  서버에서 클라이언트로부터의 메세지를 받을 api의 prefix를 설정한다
     *  @MessageMapping("/hello") in controller --> "/app/hello" is the endpoint
     *
     *  //Client Example
     *  stompClient.send("/app/hello", {}, JSON.stringify(...)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /*
     *  클라이언트에서 WebSocket을 연결할 api를 설정한다
     *
     *  //Client Example
     *  var socket = new SockJS('websockethandler');
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websockethandler").withSockJS();
    }
}
