package com.example.letsgongbu.elasticsearch;

import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.PostTestReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ESController {

    private final ESService esService;

    @PostMapping("/api/post")
    public void save(@RequestBody PostTestReq postTestReq) {
        Long id = esService.save(postTestReq);
    }

    @GetMapping("/post/{content}")
    public void search(@PathVariable String content, Pageable pageable) {
        List<Post> post1 = esService.searchByName(content, pageable);
        for (Post post : post1) {
            System.out.println(post.getTitle());
        }
    }
}