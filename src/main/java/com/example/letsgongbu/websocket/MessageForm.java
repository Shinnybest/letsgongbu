package com.example.letsgongbu.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageForm {
    private String chatroom;
    private String content;
    private String type;

    @Override
    public String toString() {
        return "MessageForm{" +
                "chatroom='" + chatroom + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
