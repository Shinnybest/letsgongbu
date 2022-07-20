package com.example.letsgongbu.service;

import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.dto.response.PostTestResp;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BoardService {
    List<Post> findAllPosts();
    List<PostTestResp> findSearchPosts(String word, Pageable p);
    void uploadPost(PostForm postForm, UserDetails userDetails);

    Post findPost(String postsTitle);

    void updatePost(String postsTitle, PostForm postForm, HttpServletRequest request);

    void deletePost(String postsTitle, HttpServletRequest request);

    PostForm getUpdatePost(String postsTitle);

    List<PostResponseDto.PostList> findAllPostsByMe(String username);
}
