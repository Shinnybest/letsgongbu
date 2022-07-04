package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Comment;
import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.dto.response.CommentResponseDto;
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
import java.util.Optional;
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

    private Post getPost(String postTitle) {
        Optional<Post> post = boardRepository.findByTitle(postTitle);
        if (!post.isPresent()) {
            // 예외처리
        }
        return post.get();
    }

    private Cookie getCookie(HttpServletRequest request) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("loginCookie")).findAny();
        if (!cookie.isPresent()) {
            // 예외처리
        }
        return cookie.get();
    }

    private Member getMember(Cookie cookie) {
        Optional<Member> member = memberRepository.findBySessionId(cookie.getValue());
        if (!member.isPresent()) {
            // 예외처리
        }
        return member.get();
    }

    private Comment getComment(Long commentsId) {
        Optional<Comment> comment = commentRepository.findById(commentsId);
        if (!comment.isPresent()) {

        }
        return comment.get();
    }
}
