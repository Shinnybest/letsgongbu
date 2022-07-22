package com.example.letsgongbu.service;

import com.example.letsgongbu.dto.request.LoginForm;
import com.example.letsgongbu.dto.response.MemberResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface MemberService {
    void kakaoLogin(String code);
    void saveInformation(LoginForm dto);
    void sendCookie(HttpSession session);
    void signup(String username, String loginId, String password);
    MemberResponseDto.MemberName getMemberName(UserDetails userDetails);
}