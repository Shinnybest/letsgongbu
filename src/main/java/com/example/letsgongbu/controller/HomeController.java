package com.example.letsgongbu.controller;

import com.example.letsgongbu.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String getMain(@ModelAttribute Member member) {
        return "home";
    }
}
