package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.dto.request.LoginForm;
import com.example.letsgongbu.dto.response.MemberResponseDto;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

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

    @Override
    public void signup(String username, String loginId, String password) {
        String encodePassword = passwordEncoder.encode(password);
        Member member = new Member(username, loginId, encodePassword);
        memberRepository.save(member);
    }

    @Override
    public MemberResponseDto.MemberName getMemberName(UserDetails userDetails) {
        return new MemberResponseDto.MemberName(userDetails.getUsername());
    }
}
