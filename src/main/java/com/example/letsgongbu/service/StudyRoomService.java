package com.example.letsgongbu.service;

import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto.All;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.util.List;

public interface StudyRoomService {
    List<All> findAll();

    void openStudyRoom(StudyRoomForm studyRoomForm, UserDetails userDetails) throws ParseException;

    StudyRoomForm findStudyRoomInformation(Long roomId);

    void updateStudyRoom(Long roomId, StudyRoomForm studyRoomForm, UserDetails userDetails);

    void deleteStudyRoom(Long roomId, UserDetails userDetails);

    void joinStudyRoom(Long roomId, UserDetails userDetails);

    void leaveStudyRoom(Long roomId, UserDetails userDetails);

    void matchMemberAndStudyRoom(Long roomId, UserDetails userDetails);

    void getNoAccess(Long roomId, UserDetails userDetails);
}
