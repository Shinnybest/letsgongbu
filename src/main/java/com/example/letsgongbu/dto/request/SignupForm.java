package com.example.letsgongbu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SignupForm {

    @NotBlank
    private String username;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
