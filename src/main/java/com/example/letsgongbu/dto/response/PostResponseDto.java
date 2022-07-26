package com.example.letsgongbu.dto.response;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private int commentCnt;
}
