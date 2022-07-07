package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.StudyRoom;
import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto.All;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.StudyRoomRepository;
import com.example.letsgongbu.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    @Override
    public List<All> findAll() {
        return studyRoomRepository.findAll()
                .stream()
                .map(s -> new All(s.getId(), s.getTitle(), s.getMainCategory(), s.getSubCategory(), s.getImgUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public void openStudyRoom(StudyRoomForm studyRoomForm, Object login) {
        StudyRoom studyRoom = StudyRoom.builder()
                                        .title(studyRoomForm.getTitle())
                                        .mainCategory(studyRoomForm.getMainCategory())
                                        .subCategory(studyRoomForm.getSubCategory())
                                        .startDay(studyRoomForm.getStartDay())
                                        .endDay(studyRoomForm.getEndDay())
                                        .imgUrl(studyRoomForm.getImgUrl())
                                        .build();
        studyRoomRepository.save(studyRoom);
    }

    @Override
    public void updateStudyRoom(Long roomId, StudyRoomForm studyRoomForm, Object login) {
        StudyRoom room = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        room.updateStudyRoom(studyRoomForm);
    }

    @Override
    public void deleteStudyRoom(Long roomId, StudyRoomForm studyRoomForm, Object login) {
        StudyRoom room = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        studyRoomRepository.delete(room);
    }
}
