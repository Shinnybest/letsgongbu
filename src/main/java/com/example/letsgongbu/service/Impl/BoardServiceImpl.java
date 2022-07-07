package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.domain.SubCategory;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.BoardRepository;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Post> findCategoryPosts(String mainCategory, String subCategory) {
        if (mainCategory.equals("ALL") & subCategory.equals("ALL")) {
            return boardRepository.findAll();
        }
        return boardRepository.findAllByMainCategoryAndSubCategory(MainCategory.valueOf(mainCategory), SubCategory.valueOf(subCategory));
    }

    @Override
    public void uploadPost(PostForm postForm, HttpServletRequest request) {
        Post post = new Post(postForm.getTitle(), postForm.getContent(), postForm.getMainCategory(), postForm.getSubCategory());
        Cookie cookie = getCookie(request);
        Member member = getMember(cookie);
        post.setMember(member);
        boardRepository.save(post);
    }

    @Override
    public Post findPost(String postsTitle) {
        return boardRepository
                .findByTitle(postsTitle)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
    }

    @Transactional
    @Override
    public void updatePost(String postsTitle, PostForm postForm, HttpServletRequest request) {
        Post post = boardRepository.findByTitle(postsTitle).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        post.updatePost(postForm);
    }

    @Transactional
    @Override
    public void deletePost(String postsTitle, HttpServletRequest request) {
        Post post = boardRepository.findByTitle(postsTitle).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        boardRepository.delete(post);
    }

    @Override
    public PostForm getUpdatePost(String postsTitle) {
        Post post = boardRepository.findByTitle(postsTitle).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        return new PostForm(post.getTitle(), post.getContent(), post.getMainCategory(), post.getSubCategory());
    }

    @Override
    public List<PostResponseDto.PostList> findAllPostsByMe(String username) {
        List<Post> posts = boardRepository.findAllByMember_Username(username);
        return posts.stream()
                .map(post -> new PostResponseDto.PostList(post.getTitle(), post.getMember().getUsername()))
                .collect(Collectors.toList());
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
}
