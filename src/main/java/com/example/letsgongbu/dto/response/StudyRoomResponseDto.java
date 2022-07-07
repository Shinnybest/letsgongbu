package com.example.letsgongbu.dto.response;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;

@Getter
@AllArgsConstructor
public class StudyRoomResponseDto {
    private Long id;
    private String title;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private SimpleDateFormat startDay;
    private SimpleDateFormat endDay;
    private String imgUrl;

    @Getter
    @AllArgsConstructor
    public static class All {
        private Long id;
        private String title;
        private MainCategory mainCategory;
        private SubCategory subCategory;
        private String imgUrl;
    }
}
