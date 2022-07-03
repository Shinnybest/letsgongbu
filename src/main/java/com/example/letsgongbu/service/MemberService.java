package com.example.letsgongbu.service;


import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.dto.request.LoginForm;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface MemberService {
    void kakaoLogin(String code);

    void saveInformation(LoginForm dto);

    void sendCookie(HttpSession session);

    Member login(String loginId, String password, boolean isChecked, HttpSession session, HttpServletResponse response);

    void signup(String username, String loginId, String password);
}
