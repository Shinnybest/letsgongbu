package com.example.letsgongbu.service;

import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.dto.response.CommentResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {
    List<CommentResponseDto> findComments(Post post);

    void saveComment(String postTitle, CommentForm commentForm, HttpServletRequest request);

    void deleteComment(Long commentsId);
}
