package com.example.letsgongbu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginForm {
    private String loginId;
    private String password;
    private Boolean open;

}
