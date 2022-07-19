package com.example.letsgongbu.elasticsearch.repository;

import com.example.letsgongbu.domain.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPostSearchRepository {
    List<Post> searchByWord(String word, Pageable pageable);
}
