package com.example.letsgongbu.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    public void handle(MessageForm messageForm) {
        System.out.println("messageForm.toString() = " + messageForm.toString());
    }
}
