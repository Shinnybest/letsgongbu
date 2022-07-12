package com.example.letsgongbu.dto.request;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.text.SimpleDateFormat;

@Getter
public class StudyRoomForm {
    private Long id;
    private String title;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private String startDay;
    private String endDay;
    private String thumbnail;

    @Builder
    public StudyRoomForm(Long id, String title, MainCategory mainCategory, SubCategory subCategory, String startDay, String endDay, String thumbnail) {
        this.id = id;
        this.title = title;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.startDay = startDay;
        this.endDay = endDay;
        this.thumbnail = thumbnail;
    }
}
