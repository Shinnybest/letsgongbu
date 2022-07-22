package com.example.letsgongbu.service;

import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto;
import com.example.letsgongbu.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.util.List;

public interface StudyRoomService {
    List<StudyRoomResponseDto> findAll();

    void openStudyRoom(StudyRoomForm studyRoomForm, UserDetailsImpl userDetails) throws ParseException;

    StudyRoomForm findStudyRoomInformation(Long roomId);

    void updateStudyRoom(Long roomId, StudyRoomForm studyRoomForm, UserDetails userDetails);

    void deleteStudyRoom(Long roomId, UserDetails userDetails);

    void joinStudyRoom(Long roomId, UserDetails userDetails);

    void leaveStudyRoom(Long roomId, UserDetails userDetails);

    void matchMemberAndStudyRoom(Long roomId, UserDetails userDetails);

    void getNoAccess(Long roomId, UserDetails userDetails);

    List<StudyRoomResponseDto> findMyStudyRooms(UserDetails userDetails);
}
