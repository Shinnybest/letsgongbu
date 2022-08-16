package com.example.letsgongbu.service;

import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.dto.response.PostResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {
//    List<CommentResponseDto> findComments(Post post);

    void saveComment(Long postId, CommentForm commentForm, UserDetails userDetails);

    void deleteComment(Long commentId, UserDetails userDetails);

    List<PostResponseDto> findAllPostsCommentedByMe(UserDetails userDetails);
}
