package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.dto.request.SignupForm;
import com.example.letsgongbu.dto.response.MemberResponseDto;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupForm signupForm) {
        String encodePassword = passwordEncoder.encode(signupForm.getPassword());
        Member member = new Member(signupForm.getUsername(), signupForm.getLoginId(), encodePassword);
        memberRepository.save(member);
    }

    @Override
    public MemberResponseDto.MemberName getMemberName(UserDetails userDetails) {
        return new MemberResponseDto.MemberName(userDetails.getUsername());
    }
}
