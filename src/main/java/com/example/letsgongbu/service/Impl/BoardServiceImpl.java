package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.domain.SubCategory;
import com.example.letsgongbu.dto.request.PostForm;
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
import java.util.Optional;

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
        Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("loginCookie")).findAny();
        if (!cookie.isPresent()) {
            // 예외처리
        }
        Optional<Member> member = memberRepository.findBySessionId(cookie.get().getValue());
        if (!member.isPresent()) {
            // 예외처리
        }
        post.setMember(member.get());
        boardRepository.save(post);
    }

    @Override
    public Post findPost(String postsTitle) {
        Optional<Post> post = boardRepository.findByTitle(postsTitle);
        if (!post.isPresent()) {
            // 예외처리
        }
        return post.get();
    }

    @Transactional
    @Override
    public void updatePost(String postsTitle, PostForm postForm, HttpServletRequest request) {
        Optional<Post> post = boardRepository.findByTitle(postsTitle);
        if (!post.isPresent()) {
            // 예외처리
        }
        post.get().updatePost(postForm);
    }

    @Transactional
    @Override
    public void deletePost(String postsTitle, HttpServletRequest request) {
        Optional<Post> post = boardRepository.findByTitle(postsTitle);
        if (!post.isPresent()) {
            // 예외처리
        }
        boardRepository.delete(post.get());
    }

    @Override
    public PostForm getUpdatePost(String postsTitle) {
        Optional<Post> post = boardRepository.findByTitle(postsTitle);
        if (!post.isPresent()) {
            // 예외처리
        }
        return new PostForm(post.get().getTitle(), post.get().getContent(), post.get().getMainCategory(), post.get().getSubCategory());
    }
}
