package com.example.letsgongbu.service;


import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.PostUpdateForm;
import com.example.letsgongbu.dto.response.PostAndCommentResponseDto;
import com.example.letsgongbu.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BoardService {
    List<PostResponseDto> findAllPosts();
    List<PostResponseDto> findSearchPosts(String word, Pageable p);
    Long uploadPost(PostForm postForm, String username);

    PostAndCommentResponseDto findPostAndComments(Long postId, String username);

    void updatePost(Long postId, PostForm postForm, String username);

    void deletePost(Long postId, String username);

    PostUpdateForm getUpdatePost(Long postId, String username);

    List<PostResponseDto> findAllPostsByMe(String username);
}
