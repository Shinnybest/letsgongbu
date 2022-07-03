package com.example.letsgongbu.controller;

import com.example.letsgongbu.domain.Member;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        return "/login/login-page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpSession session,
                        HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "/login/login-page";
        }

        Member member = memberService.login(loginForm.getLoginId(),
                                            loginForm.getPassword(),
                                            loginForm.getOpen(),
                                            session,
                                            response);

        if (member == null) {
            bindingResult.reject("loginFail", "등록되지 않은 사용자입니다. 아이디를 다시 한번 확인해주세요.");
            return "/login/login-page";
        }

        return "redirect:" + redirectURL;
    }

    @GetMapping("/sign-up")
    public String getSignupPage(@ModelAttribute SignupForm signupForm) {
        return "/login/sign-up";
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
        return "/login/information";
        // TODO 기존 고객이면 바로 "redirect:/";
    }

    @PostMapping("/login/information")
    public String saveInformation(LoginForm dto) {
        memberService.saveInformation(dto);
        return "redirect:/";
    }
}
