package com.example.letsgongbu.dto.response;

import com.example.letsgongbu.domain.Comment;
import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostAndCommentResponseDto {
    private Long id;
    private String title;
    private String postContent;
    private String postWriter;
    private LocalDateTime postCreatedAt;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private int commentCnt;
    private List<CommentResponseDto> comments;

    @Builder
    public PostAndCommentResponseDto(Long id, String title, String postContent, String postWriter, LocalDateTime postCreatedAt, MainCategory mainCategory, SubCategory subCategory, int commentCnt, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.postContent = postContent;
        this.postWriter = postWriter;
        this.postCreatedAt = postCreatedAt;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.commentCnt = commentCnt;
        this.comments = comments.stream().map(comment -> new CommentResponseDto(comment.getId(), comment.getContent(), comment.getMember().getUsername(), comment.getCreatedAt())).collect(Collectors.toList());
    }
}
