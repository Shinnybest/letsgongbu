package com.example.letsgongbu.elasticsearch.repository;

import com.example.letsgongbu.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomPostSearchRepositoryImpl implements CustomPostSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<PostResponseDto> searchByWord(String word, Pageable p) {
        Criteria criteria = Criteria.where("content").contains(word);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<PostResponseDto> search = elasticsearchOperations.search(query, PostResponseDto.class, IndexCoordinates.of("post"));
        return search.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
