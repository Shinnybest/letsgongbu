package com.example.letsgongbu.controller;

import com.example.letsgongbu.dto.request.LoginForm;
import com.example.letsgongbu.dto.request.SignupForm;
import com.example.letsgongbu.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.view.ThymeleafView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginPage";
    }

    @GetMapping("/sign-up")
    public String getSignupPage(@ModelAttribute("signupForm") SignupForm signupForm) {
        return "login/sign-up";
    }

    @PostMapping("/sign-up")
    public String signup(@Valid @ModelAttribute SignupForm signupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/login/sign-up";
        }
        memberService.signup(signupForm.getUsername(), signupForm.getLoginId(), signupForm.getPassword());
        return "redirect:/login";
    }


    @PostMapping("/login/kakao")
    public String kakaoLogin(@RequestParam String code, HttpSession session) {
        memberService.kakaoLogin(code);
        memberService.sendCookie(session);
        return "redirect:/login/information";
    }

    @GetMapping("/login/information")
    public String getInformationPage() {
        // TODO 회원가입이면
        return "login/information";
        // TODO 기존 고객이면 바로 "redirect:/";
    }

    @PostMapping("/login/information")
    public String saveInformation(LoginForm dto) {
        memberService.saveInformation(dto);
        return "redirect:/";
    }

    @PostMapping("/withdrawal")
    public String removeUser() {
        return "redirect:/";
    }
}
