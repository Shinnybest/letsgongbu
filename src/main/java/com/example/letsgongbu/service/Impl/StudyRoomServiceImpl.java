package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.MemberStudyRoom;
import com.example.letsgongbu.domain.StudyRoom;
import com.example.letsgongbu.dto.request.StudyRoomForm;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto.All;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.repository.MemberStudyRoomRepository;
import com.example.letsgongbu.repository.StudyRoomRepository;
import com.example.letsgongbu.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final MemberRepository memberRepository;
    private final MemberStudyRoomRepository memberStudyRoomRepository;

    @Override
    public List<All> findAll() {
        return studyRoomRepository.findAll()
                .stream()
                .map(s -> new All(s.getId(), s.getTitle(), s.getMainCategory(), s.getSubCategory(), s.getThumbnail()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void openStudyRoom(StudyRoomForm studyRoomForm, UserDetails userDetails) throws ParseException {
        String startDay = studyRoomForm.getStartDay();
        String endDay = studyRoomForm.getEndDay();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDay = format.parse(startDay);
        Date parsedEndDay = format.parse(endDay);

        String owner = userDetails.getUsername();

        StudyRoom studyRoom = StudyRoom.builder()
                                        .title(studyRoomForm.getTitle())
                                        .mainCategory(studyRoomForm.getMainCategory())
                                        .subCategory(studyRoomForm.getSubCategory())
                                        .startDay(parsedStartDay)
                                        .endDay(parsedEndDay)
                                        .thumbnail(studyRoomForm.getThumbnail())
                                        .createdBy(owner)
                                        .build();
        studyRoomRepository.save(studyRoom);
    }

    @Override
    public StudyRoomForm findStudyRoomInformation(Long roomId) {
        StudyRoom room = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        return StudyRoomForm.builder()
                .id(room.getId())
                .title(room.getTitle())
                .thumbnail(room.getThumbnail())
                .mainCategory(room.getMainCategory())
                .subCategory(room.getSubCategory())
                .startDay(String.valueOf(room.getStartDay()))
                .endDay(String.valueOf(room.getEndDay()))
                .build();
    }

    @Override
    @Transactional
    public void updateStudyRoom(Long roomId, StudyRoomForm studyRoomForm, UserDetails userDetails) {
        StudyRoom room = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        if (!room.getCreatedBy().equals(userDetails.getUsername())) {
            throw new CustomException(Error.NO_CONTROL_OVER_STUDYROOM);
        }
        room.update(studyRoomForm);
    }

    @Override
    @Transactional
    public void deleteStudyRoom(Long roomId, UserDetails userDetails) {
        StudyRoom room = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        if (!room.getCreatedBy().equals(userDetails.getUsername())) {
            throw new CustomException(Error.NO_CONTROL_OVER_STUDYROOM);
        }
        studyRoomRepository.delete(room);
    }

    @Override
    @Transactional
    public void joinStudyRoom(Long roomId, UserDetails userDetails) {
        String username = userDetails.getUsername();
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new CustomException(Error.MEMBER_NOT_EXIST));
        StudyRoom room = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        if (room.getMemberStudyRooms().size() == 20) {
            throw new CustomException(Error.STUDYROOM_IS_FULL);
        }
        if (room.getMemberStudyRooms().stream().anyMatch(m -> m.getMember().getUsername().equals(username))) {
            throw new CustomException(Error.ALREADY_IN_STUDYROOM);
        }
        memberStudyRoomRepository.save(new MemberStudyRoom(member, room));

    }

    @Override
    @Transactional
    public void leaveStudyRoom(Long roomId, UserDetails userDetails) {
        String username = userDetails.getUsername();
        StudyRoom room = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        if (Objects.equals(room.getCreatedBy(), username)) {
            throw new CustomException(Error.OWNER_CANNOT_LEAVE);
        }
        MemberStudyRoom memberStudyRoom = memberStudyRoomRepository.findByStudyRoom(room).orElseThrow(() -> new CustomException(Error.MEMBER_STUDYROOM_NOT_FOUND));
        memberStudyRoomRepository.delete(memberStudyRoom);
    }

    @Override
    public void matchMemberAndStudyRoom(Long roomId, UserDetails userDetails) {
        StudyRoom studyRoom = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        if (studyRoom.getMemberStudyRooms().stream().noneMatch(member -> member.getMember().getUsername().equals(userDetails.getUsername()))) {
            throw new CustomException(Error.NOT_JOINED_STUDYROOM);
        }
    }

    @Override
    public void getNoAccess(Long roomId, UserDetails userDetails) {
        StudyRoom studyRoom = studyRoomRepository.findById(roomId).orElseThrow(() -> new CustomException(Error.STUDYROOM_NOT_FOUND));
        if (!studyRoom.getCreatedBy().equals(userDetails.getUsername())) {
            throw new CustomException(Error.NO_CONTROL_OVER_STUDYROOM);
        }
    }
}
