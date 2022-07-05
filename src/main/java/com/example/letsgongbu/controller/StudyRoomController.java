package com.example.letsgongbu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudyRoomController {

    @GetMapping("/studyroom")
    public String getStudyRoomPage(){
        return "/studyroom/study-room";
    }
}
