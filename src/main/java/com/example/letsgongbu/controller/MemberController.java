package com.example.letsgongbu.controller;

import com.example.letsgongbu.dto.request.LoginForm;
import com.example.letsgongbu.dto.request.SignupForm;
import com.example.letsgongbu.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
            return "login/sign-up";
        }
        memberService.signup(signupForm);
        return "redirect:/login";
    }

    @PostMapping("/withdrawal")
    public String removeUser() {
        return "redirect:/";
    }
}
