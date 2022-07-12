package com.example.letsgongbu.domain;


import com.example.letsgongbu.dto.request.StudyRoomForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private MainCategory mainCategory;

    @Column
    private SubCategory subCategory;

    @Column
    private String title;

    @Column
    private Date startDay;

    @Column
    private Date endDay;

    @Column
    private String thumbnail;

    @Column
    private String createdBy;

    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberStudyRoom> memberStudyRooms = new ArrayList<>();

    @Builder
    public StudyRoom(MainCategory mainCategory, SubCategory subCategory, String title, Date startDay, Date endDay, String thumbnail, String createdBy) {
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.title = title;
        this.startDay = startDay;
        this.endDay = endDay;
        this.thumbnail = thumbnail;
        this.createdBy = createdBy;
    }

    public void update(StudyRoomForm studyRoomForm) {
        this.title = studyRoomForm.getTitle();
        this.thumbnail = studyRoomForm.getThumbnail();
    }
}