package com.example.letsgongbu.dto.response;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class StudyRoomResponseDto {
    private Long id;
    private String title;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private Date startDay;
    private Date endDay;
    private String imgUrl;
}
