package com.example.letsgongbu.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/{chatroom}")
    @SendTo("/topic/{chatroom}")
    public ChatMessage handle(@Payload ChatMessage chatMessage, @DestinationVariable String chatroom, java.security.Principal principal) {
        chatMessage.addWriter(principal.getName());
        return chatMessage;
    }
}
