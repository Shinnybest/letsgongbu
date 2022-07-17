package com.example.letsgongbu.elasticsearch;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Getter
@Document(indexName = "post")
public class ESPost {
    @Id
    private Long id;
    private String title;

    public ESPost(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
