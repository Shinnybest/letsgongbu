package com.example.letsgongbu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PostChange {

    @Getter
    @AllArgsConstructor
    public static class PostList {
        private String title;
        private String username;
    }
}
