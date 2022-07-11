package com.example.letsgongbu.filter;

import com.example.letsgongbu.dto.response.MemberResponseDto;
import com.example.letsgongbu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Cookie cookie = WebUtils.getCookie(request, "loginCookie");
        if (cookie != null) {
            MemberResponseDto.MemberName dto = memberService.checkAutoLogin(cookie.getValue());
            if (dto != null) {
                session.setAttribute("LOGIN", dto);
            }
        }
        return true;
    }
}
