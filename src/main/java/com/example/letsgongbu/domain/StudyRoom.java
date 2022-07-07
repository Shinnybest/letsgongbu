package com.example.letsgongbu.domain;


import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.StudyRoomForm;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.text.SimpleDateFormat;


@Entity
@Getter
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
    private SimpleDateFormat startDay;

    @Column
    private SimpleDateFormat endDay;

    @Column
    private String imgUrl;

    @Builder
    public StudyRoom(MainCategory mainCategory, SubCategory subCategory, String title, SimpleDateFormat startDay, SimpleDateFormat endDay, String imgUrl) {
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.title = title;
        this.startDay = startDay;
        this.endDay = endDay;
        this.imgUrl = imgUrl;
    }

    public void updateStudyRoom(StudyRoomForm studyRoomForm) {
        this.title = studyRoomForm.getTitle();
        this.imgUrl = studyRoomForm.getImgUrl();
    }
}