package com.example.letsgongbu.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String chatroom;
    private String content;
    private String type;
    private String writer;

    public void addWriter(String writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "chatroom='" + chatroom + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}
