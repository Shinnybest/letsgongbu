package com.example.letsgongbu.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class StompOutHandler implements ChannelInterceptor {

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        System.out.println("StompOutHandler 호출 : " + message.getPayload());
    }
}
