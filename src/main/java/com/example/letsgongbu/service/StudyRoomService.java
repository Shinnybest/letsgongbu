package com.example.letsgongbu.service;

import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto.All;

import java.util.List;

public interface StudyRoomService {
    List<All> findAll();

    void openStudyRoom(StudyRoomForm studyRoomForm, Object login);

    void updateStudyRoom(Long roomId, StudyRoomForm studyRoomForm, Object login);

    void deleteStudyRoom(Long roomId, StudyRoomForm studyRoomForm, Object login);

    void joinStudyRoom(Long roomId, StudyRoomForm studyRoomForm, Object login);

    void leaveStudyRoom(Long roomId, StudyRoomForm studyRoomForm, Object login);
}
