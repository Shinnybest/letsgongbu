package com.example.letsgongbu.dto.response;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostTestResp {
    private String title;
    private String content;
    private MainCategory mainCategory;
    private SubCategory subCategory;

}
