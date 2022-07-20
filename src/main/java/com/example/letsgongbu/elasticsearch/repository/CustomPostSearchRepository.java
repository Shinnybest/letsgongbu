package com.example.letsgongbu.elasticsearch.repository;

import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.response.PostTestResp;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPostSearchRepository {
    List<PostTestResp> searchByWord(String word, Pageable pageable);
}
