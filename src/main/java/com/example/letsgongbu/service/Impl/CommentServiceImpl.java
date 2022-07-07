package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Comment;
import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.dto.response.CommentResponseDto;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.BoardRepository;
import com.example.letsgongbu.repository.CommentRepository;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<CommentResponseDto> findComments(Post post) {
        return post.getComments()
                .stream()
                .map(c -> new CommentResponseDto(c.getId(), c.getContent(), c.getMember().getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveComment(String postTitle, CommentForm commentForm, HttpServletRequest request) {
        Comment comment = new Comment(commentForm.getContent());
        Member member = getMember(getCookie(request));
        comment.setMember(member);
        Post post = getPost(postTitle);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentsId) {
        commentRepository.delete(getComment(commentsId));
    }

    @Override
    public List<PostResponseDto.PostList> findAllPostsCommentedByMe(String username) {
        List<Post> posts = boardRepository.findAllByCommentByMe(username);
        return posts.stream()
                .map(post -> new PostResponseDto.PostList(post.getTitle(), post.getMember().getUsername()))
                .collect(Collectors.toList());
    }

    private Post getPost(String postTitle) {
        return boardRepository
                .findByTitle(postTitle)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
    }

    private Cookie getCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("loginCookie"))
                .findAny()
                .orElseThrow(() -> new CustomException(Error.COOKIE_NOT_FOUND));
    }

    private Member getMember(Cookie cookie) {
        return memberRepository
                .findBySessionId(cookie.getValue())
                .orElseThrow(() -> new CustomException(Error.MEMBER_NOT_EXIST));
    }

    private Comment getComment(Long commentsId) {
        return commentRepository
                .findById(commentsId)
                .orElseThrow(() -> new CustomException(Error.COMMENT_NOT_FOUND));
    }
}
