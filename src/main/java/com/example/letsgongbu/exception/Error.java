package com.example.letsgongbu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {

    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "404", "존재하지 않는 사용자입니다."),
    COOKIE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401", "쿠키가 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "존재하지 않는 게시글입니다."),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "존재하지 않는 댓글입니다."),
    STUDYROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "존재하지 않는 스터디룸입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}
