package com.example.letsgongbu.service;

import com.example.letsgongbu.dto.request.SignupForm;
import com.example.letsgongbu.dto.response.MemberResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberService {
    void signup(SignupForm signupForm);
    MemberResponseDto.MemberName getMemberName(UserDetails userDetails);
}