package com.example.letsgongbu.controller;

import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto.All;
import com.example.letsgongbu.repository.StudyRoomRepository;
import com.example.letsgongbu.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @GetMapping("/studyroom/all")
    public String getStudyRoomPage(Model model){
        List<All> dtoList = studyRoomService.findAll();
        model.addAttribute("studyrooms", dtoList);
        return "/studyroom/all-rooms";
    }

    @PostMapping("/studyroom/new")
    public String openStudyRoom(@ModelAttribute("StudyRoomForm") StudyRoomForm studyRoomForm,
                                HttpSession session) {
        studyRoomService.openStudyRoom(studyRoomForm, session.getAttribute("LOGIN"));
        return "redirect:/studyroom/all";
    }

    @PostMapping("/studyroom/{roomId}/update")
    public String updateStudyRoom(@PathVariable Long roomId, @ModelAttribute("StudyRoomForm") StudyRoomForm studyRoomForm,
                                HttpSession session) {
        studyRoomService.updateStudyRoom(roomId, studyRoomForm, session.getAttribute("LOGIN"));
        return "redirect:/studyroom/all";
    }

    @PostMapping("/studyroom/{roomId}/delete")
    public String deleteStudyRoom(@PathVariable Long roomId, @ModelAttribute("StudyRoomForm") StudyRoomForm studyRoomForm,
                                  HttpSession session) {
        studyRoomService.deleteStudyRoom(roomId, studyRoomForm, session.getAttribute("LOGIN"));
        return "redirect:/studyroom/all";
    }

    @PostMapping("/studyroom/join/{roomId}")
    public String joinStudyRoom(@PathVariable Long roomId, @ModelAttribute("StudyRoomForm") StudyRoomForm studyRoomForm,
                                  HttpSession session) {
        studyRoomService.joinStudyRoom(roomId, studyRoomForm, session.getAttribute("LOGIN"));
        return "redirect:/studyroom/all";
    }

    @PostMapping("/studyroom/leave/{roomId}")
    public String leaveStudyRoom(@PathVariable Long roomId, @ModelAttribute("StudyRoomForm") StudyRoomForm studyRoomForm,
                                HttpSession session) {
        studyRoomService.leaveStudyRoom(roomId, studyRoomForm, session.getAttribute("LOGIN"));
        return "redirect:/studyroom/all";
    }

    @GetMapping("/studyroom/{roomId}")
    public String getStudyRoomPage(@PathVariable Long roomId) {
        // todo 사용자 확인 후 입장 or 입장불가 표시
        return "/studyroom/study-room";
    }
}
