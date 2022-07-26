package com.example.letsgongbu.dto.request;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostUpdateForm {
    private Long id;
    private String title;
    private String content;
    private MainCategory mainCategory;
    private SubCategory subCategory;
}
