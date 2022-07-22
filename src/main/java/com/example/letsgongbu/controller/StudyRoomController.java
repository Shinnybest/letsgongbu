package com.example.letsgongbu.controller;

import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto;
import com.example.letsgongbu.security.UserDetailsImpl;
import com.example.letsgongbu.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    // 모든 스터디룸 보기
    @GetMapping("/study-room/all")
    public String enterStudyRoom(Model model){
        List<StudyRoomResponseDto> studyRooms = studyRoomService.findAll();
        model.addAttribute("studyrooms", studyRooms);
        return "study-room/all-rooms";
    }

    // 스터디룸 생성 페이지
    @GetMapping("/study-room/new")
    public String getCreateStudyRoomPage(@ModelAttribute StudyRoomForm studyRoomForm){
        return "study-room/create-study-room";
    }

    // 스터디룸 생성
    @PostMapping("/study-room/new")
    public String openStudyRoom(@ModelAttribute StudyRoomForm studyRoomForm,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws ParseException {
        studyRoomService.openStudyRoom(studyRoomForm, userDetails);
        return "redirect:/study-room/all";
    }

    // 스터디룸 수정 페이지
    @GetMapping("/study-room/{roomId}/edit")
    public String getEditStudyRoomPage(@PathVariable Long roomId,
                                       Model model,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.getNoAccess(roomId, userDetails);
        StudyRoomForm studyRoomForm = studyRoomService.findStudyRoomInformation(roomId);
        model.addAttribute("studyRoomForm", studyRoomForm);
        return "study-room/edit-study-room";
    }
    
    // 스터디룸 설정 정보 업데이트
    @PostMapping("/study-room/{roomId}/update")
    public String updateStudyRoom(@PathVariable Long roomId,
                                  @ModelAttribute StudyRoomForm studyRoomForm,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.updateStudyRoom(roomId, studyRoomForm, userDetails);
        return "redirect:/study-room/all";
    }

    // 스터디룸 삭제
    @PostMapping("/study-room/{roomId}/delete")
    public String deleteStudyRoom(@PathVariable Long roomId,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.deleteStudyRoom(roomId, userDetails);
        return "redirect:/study-room/all";
    }

    // 새 스터디룸 입장
    @PostMapping("/study-room/join/{roomId}")
    public String joinStudyRoom(@PathVariable Long roomId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.joinStudyRoom(roomId, userDetails);
        return "redirect:/study-room?roomId=" + roomId;
    }

    // 스터디룸 떠나기
    @PostMapping("/study-room/leave/{roomId}")
    public String leaveStudyRoom(@PathVariable Long roomId,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.leaveStudyRoom(roomId, userDetails);
        return "redirect:/study-room/all";
    }

    // 참여중인 스터디룸 입장
    @GetMapping("/study-room")
    public String enterStudyRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetails userDetails) {
        studyRoomService.matchMemberAndStudyRoom(roomId, userDetails);
        return "study-room/in-study-room";
    }
}
