package com.example.letsgongbu.elasticsearch.repository;

import com.example.letsgongbu.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPostSearchRepository {
    List<PostResponseDto> searchByWord(String word, Pageable pageable);
}
