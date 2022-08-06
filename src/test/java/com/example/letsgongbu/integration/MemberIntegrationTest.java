package com.example.letsgongbu.integration;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.dto.request.SignupForm;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("회원가입")
    void signup() {
        // given
        SignupForm signupForm = new SignupForm("user123", "id123", "pw123");
        // when
        memberService.signup(signupForm);
        // then
        Member member = memberRepository.findByUsername(signupForm.getUsername()).get();
        Assertions.assertEquals(signupForm.getUsername(), member.getUsername());
        Assertions.assertEquals(signupForm.getLoginId(), member.getLoginId());
    }
}
