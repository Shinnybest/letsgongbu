package com.example.letsgongbu.elasticsearch.repository;

import com.example.letsgongbu.elasticsearch.ESPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ESPostRepository extends ElasticsearchRepository<ESPost, Long> {
    Page<ESPost> findByTitle(String title, Pageable pageable);
}
