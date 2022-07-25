package com.example.letsgongbu.controller;


import com.example.letsgongbu.dto.request.SignupForm;
import com.example.letsgongbu.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberService memberService;


    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                        new MemberController(memberService)
                )
                .build();
    }

    @Test
    @DisplayName("GET : 로그인 페이지")
    void getLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginPage"));
    }

    @Test
    @DisplayName("GET : 회원가입 페이지")
    void getSignupPage() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/sign-up"));
    }

    @Test
    @DisplayName("POST : 회원가입")
    void signup() throws Exception {
        SignupForm signupForm = new SignupForm("Sonny", "Kane", "Football");
        mockMvc.perform(post("/sign-up")
                        .requestAttr("signupForm", signupForm))
        .andExpect(status().isOk());
    }
}