package com.example.letsgongbu.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MemberStudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDYROOM_ID")
    private StudyRoom studyRoom;

    public MemberStudyRoom(Member member, StudyRoom studyRoom) {
        setMember(member);
        setStudyRoom(studyRoom);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getMemberStudyRooms().add(this);
    }

    public void setStudyRoom(StudyRoom studyRoom) {
        this.studyRoom = studyRoom;
        studyRoom.getMemberStudyRooms().add(this);
    }
}
