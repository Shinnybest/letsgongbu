package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Comment;
import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.BoardRepository;
import com.example.letsgongbu.repository.CommentRepository;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void saveComment(Long postId, CommentForm commentForm, UserDetails userDetails) {
        Comment comment = new Comment(commentForm.getContent());
        Member member = existsMember(userDetails);
        Post post = existsPost(postId);
        comment.setMember(member);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId, UserDetails userDetails) {
        commentRepository.delete(existsComment(commentId));
    }

    @Override
    public List<PostResponseDto> findAllPostsCommentedByMe(UserDetails userDetails) {
        Member member = existsMember(userDetails);
        List<Post> posts = boardRepository.findAllByCommentByMe(member.getUsername());
        return posts.stream()
                .map(post -> new PostResponseDto(post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getMember().getUsername(),
                        post.getCreatedAt(),
                        post.getMainCategory(),
                        post.getSubCategory(),
                        post.getComments().size())).collect(Collectors.toList());
    }

    private Member existsMember(UserDetails userDetails) {
        return memberRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new CustomException(Error.MEMBER_NOT_EXIST));
    }

    private Post existsPost(Long postId) {
        return boardRepository
                .findById(postId)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
    }

    private Comment existsComment(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CustomException(Error.COMMENT_NOT_FOUND));
    }
}
