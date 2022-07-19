package com.example.letsgongbu.elasticsearch;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.domain.SubCategory;
import com.example.letsgongbu.dto.request.PostTestReq;
import com.example.letsgongbu.elasticsearch.repository.CustomPostSearchRepository;
import com.example.letsgongbu.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ESService {

    private final BoardRepository boardRepository;
    private final CustomPostSearchRepository postSearchRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Transactional
    public Long save(PostTestReq postTestReq) {
        Post post = new Post(postTestReq.getTitle(), postTestReq.getContent(), MainCategory.CS, SubCategory.OS);
        Post savedPost = boardRepository.save(post);
        elasticsearchRestTemplate.save(post, IndexCoordinates.of("hello"));
        return savedPost.getId();
    }

    public List<Post> searchByName(String title, Pageable pageable) {
        // userSearchRepository.findByBasicProfile_NameContains(name) 가능
        return postSearchRepository.searchByWord(title, pageable);
    }
}
