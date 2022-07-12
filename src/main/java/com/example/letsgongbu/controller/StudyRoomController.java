package com.example.letsgongbu.controller;

import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto.All;
import com.example.letsgongbu.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @GetMapping("/studyroom/all")
    public String enterStudyRoom(Model model){
        List<All> dtoList = studyRoomService.findAll();
        model.addAttribute("studyrooms", dtoList);
        return "/studyroom/all-rooms";
    }

    @GetMapping("/studyroom/new")
    public String getCreateStudyRoomPage(@ModelAttribute StudyRoomForm studyRoomForm){
        return "/studyroom/create-study-room";
    }

    @PostMapping("/studyroom/new")
    public String openStudyRoom(@ModelAttribute StudyRoomForm studyRoomForm,
                                @AuthenticationPrincipal UserDetails userDetails) throws ParseException {
        studyRoomService.openStudyRoom(studyRoomForm, userDetails);
        return "redirect:/studyroom/all";
    }

    @GetMapping("/studyroom/{roomId}/edit")
    public String getEditStudyRoomPage(@PathVariable Long roomId,
                                       Model model,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.getNoAccess(roomId, userDetails);
        StudyRoomForm studyRoomForm = studyRoomService.findStudyRoomInformation(roomId);
        model.addAttribute("studyRoomForm", studyRoomForm);
        return "/studyroom/edit-study-room";
    }

    @PostMapping("/studyroom/{roomId}/update")
    public String updateStudyRoom(@PathVariable Long roomId,
                                  @ModelAttribute StudyRoomForm studyRoomForm,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.updateStudyRoom(roomId, studyRoomForm, userDetails);
        return "redirect:/studyroom/all";
    }

    @PostMapping("/studyroom/{roomId}/delete")
    public String deleteStudyRoom(@PathVariable Long roomId,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.deleteStudyRoom(roomId, userDetails);
        return "redirect:/studyroom/all";
    }

    @PostMapping("/studyroom/join/{roomId}")
    public String joinStudyRoom(@PathVariable Long roomId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.joinStudyRoom(roomId, userDetails);
        return "redirect:/studyroom?roomId=" + roomId;
    }

    @PostMapping("/studyroom/leave/{roomId}")
    public String leaveStudyRoom(@PathVariable Long roomId,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.leaveStudyRoom(roomId, userDetails);
        return "redirect:/studyroom/all";
    }

    @GetMapping("/studyroom")
    public String enterStudyRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.matchMemberAndStudyRoom(roomId, userDetails);
        return "/studyroom/in-study-room";
    }
}
