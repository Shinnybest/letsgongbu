package com.example.letsgongbu.service;


import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.PostUpdateForm;
import com.example.letsgongbu.dto.response.PostAndCommentResponseDto;
import com.example.letsgongbu.dto.response.PostChange;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.security.UserDetailsImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface BoardService {
    List<PostResponseDto> findAllPosts();
    List<PostResponseDto> findSearchPosts(String word, Pageable p);
    Long uploadPost(PostForm postForm, UserDetails userDetails);

    PostAndCommentResponseDto findPostAndComments(Long postId, UserDetails userDetails);

    void updatePost(Long postId, PostForm postForm, UserDetailsImpl userDetails);

    void deletePost(Long postId, UserDetailsImpl userDetails);

    PostUpdateForm getUpdatePost(Long postId, UserDetails userDetails);

    List<PostResponseDto> findAllPostsByMe(UserDetails userDetails);
}
