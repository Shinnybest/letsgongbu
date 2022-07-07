package com.example.letsgongbu.dto.request;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.text.SimpleDateFormat;

@Getter
@AllArgsConstructor
public class StudyRoomForm {
    private String title;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private SimpleDateFormat startDay;
    private SimpleDateFormat endDay;
    private String imgUrl;
}
