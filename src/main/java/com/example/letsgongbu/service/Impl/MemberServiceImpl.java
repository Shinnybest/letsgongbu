package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.dto.request.LoginForm;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Optional;

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
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);
        if (memberOptional.isPresent()) {
            memberOptional.filter(member -> member.getPassword().equals(password))
                    .orElseThrow(IllegalStateException::new);
            // Todo 에러가 아니라 Status Code 200 으로 메세지만 전달!
        }
        Member member = memberOptional.get();
        if (session.getAttribute("login") != null) {
            session.removeAttribute("login");
        }
        member.recordSessionId(session.getId(), LocalDate.now());
        session.setAttribute("login", member);
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
}
