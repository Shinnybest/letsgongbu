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
    STUDYROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "존재하지 않는 스터디룸입니다."),
    STUDYROOM_IS_FULL(HttpStatus.UNAUTHORIZED, "401", "스터디룸의 정원이 다 찼습니다."),
    NO_CONTROL_OVER_STUDYROOM(HttpStatus.UNAUTHORIZED, "401", "스터디룸 삭제, 수정 권한이 없습니다."),
    ALREADY_IN_STUDYROOM(HttpStatus.BAD_REQUEST, "400", "이미 참여 중인 스터디룸입니다."),
    OWNER_CANNOT_LEAVE(HttpStatus.BAD_REQUEST, "400", "방장은 스터디룸을 떠날 수 없습니다."),
    NOT_JOINED_STUDYROOM(HttpStatus.UNAUTHORIZED, "401", "참여 중인 스터디룸이 아닙니다."),
    MEMBER_STUDYROOM_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401", "참여 중인 스터디룸이 아닙니다."),
    NO_AUTHORITY_POST(HttpStatus.UNAUTHORIZED, "401", "해당 게시물에 수정/삭제 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}