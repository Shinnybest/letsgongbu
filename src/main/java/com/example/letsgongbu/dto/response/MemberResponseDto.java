package com.example.letsgongbu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberResponseDto {

    @Getter
    @AllArgsConstructor
    public static class MemberName {
        private String username;
    }
}
