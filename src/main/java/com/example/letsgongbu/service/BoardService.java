package com.example.letsgongbu.service;

import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.PostForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BoardService {
    List<Post> findCategoryPosts(String mainCategory, String subCategory);

    void uploadPost(PostForm postForm, HttpServletRequest request);

    Post findPost(String postsTitle);

    void updatePost(String postsTitle, PostForm postForm, HttpServletRequest request);

    void deletePost(String postsTitle, HttpServletRequest request);

    PostForm getUpdatePost(String postsTitle);
}
