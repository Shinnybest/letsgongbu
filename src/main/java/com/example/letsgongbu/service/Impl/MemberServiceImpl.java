package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.dto.request.LoginForm;
import com.example.letsgongbu.dto.response.MemberResponseDto;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private static final int COOKIE_VALIDATION_TIME = 60 * 60 * 24 * 7;

    @Override
    public void kakaoLogin(String code) {
        // TODO 인가코드로 토큰 받기
        // TODO 토큰으로 사용자 정보 가져오기
        // TODO 회원가입 O : 사용자 정보 가져와서 저장하기
        // TODO 회원가입 X : return
    }

    @Override
    public void saveInformation(LoginForm dto) {
        // TODO 추가 정보 저장하기
    }

    @Override
    public void sendCookie(HttpSession session) {
        // TODO 세션 확인 후 발급
        // TODO 쿠키 발급 후 전달
    }

    @Transactional
    @Override
    public Member login(String loginId, String password, boolean isChecked, HttpSession session, HttpServletResponse response) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new CustomException(Error.MEMBER_NOT_EXIST));
        // todo 비밀번호 불일치
        member.recordSessionId(session.getId());
        session.setAttribute("LOGIN", member);
        Cookie cookie = new Cookie("loginCookie", session.getId());
        cookie.setPath("/");
        if (isChecked) {
            cookie.setMaxAge(COOKIE_VALIDATION_TIME);
        }
        response.addCookie(cookie);
        return member;
    }

    @Override
    public void signup(String username, String loginId, String password) {
        Member member = new Member(username, loginId, password, null, null);
        memberRepository.save(member);
    }



    @Override
    public MemberResponseDto.MemberName getMemberName(HttpServletRequest request) {
        Member member = getMember(getCookie(request));
        return new MemberResponseDto.MemberName(member.getUsername());
    }

    @Override
    public MemberResponseDto.MemberName checkAutoLogin(String value) {
        Member member = memberRepository.findBySessionId(value).orElse(null);
        if (member==null) {
            return null;
        }
        return new MemberResponseDto.MemberName(member.getUsername());
    }

    private Cookie getCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("loginCookie"))
                .findAny()
                .orElseThrow(() -> new CustomException(Error.COOKIE_NOT_FOUND));
    }

    public Member getMember(Cookie cookie) {
        return memberRepository
                .findBySessionId(cookie.getValue())
                .orElseThrow(() -> new CustomException(Error.COOKIE_NOT_FOUND));
    }
}
