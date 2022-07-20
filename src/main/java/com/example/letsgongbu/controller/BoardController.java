package com.example.letsgongbu.controller;

import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.PostTestReq;
import com.example.letsgongbu.dto.request.SearchForm;
import com.example.letsgongbu.dto.response.CommentResponseDto;
import com.example.letsgongbu.dto.response.PostTestResp;
import com.example.letsgongbu.service.BoardService;
import com.example.letsgongbu.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;


    // 전체 게시글 조회
    @GetMapping("/posts")
    public String getPosts(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
        List<Post> posts = boardService.findAllPosts();
        model.addAttribute("posts", posts);
        return "board/posts";
    }

    // 검색하기
    @GetMapping("/search")
    public String getSearchPosts(@ModelAttribute("searchForm") SearchForm searchForm, Pageable p, Model model) {
        List<PostTestResp> posts = boardService.findSearchPosts(searchForm.getWord(), p);
        model.addAttribute("posts", posts);
        return "board/posts";
    }

    // 상세 게시글 보기
    @GetMapping("/posts/{postsTitle}")
    public String getOnePost(@PathVariable String postsTitle, Model model, @ModelAttribute("commentForm") CommentForm commentForm) {
        Post post = boardService.findPost(postsTitle);
        List<CommentResponseDto> comments = commentService.findComments(post);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "board/read-post";
    }

    // 게시글 작성 페이지
    @GetMapping("/posts/new")
    public String getOnePost(@ModelAttribute PostForm postForm) {
        return "board/upload-post";
    }

    // 게시글 작성하기
    @PostMapping("/posts")
    public String uploadPost(@ModelAttribute("postForm") PostForm postForm, @AuthenticationPrincipal UserDetails userDetails) {
        boardService.uploadPost(postForm, userDetails);
        String title = new String(postForm.getTitle().getBytes(StandardCharsets.UTF_8));
        return "redirect:/posts/" + title;
    }

    // 게시글 수정 페이지
    @GetMapping("/posts/{postsTitle}/update")
    public String getUpdatePost(@PathVariable String postsTitle, Model model) {
        PostForm postForm = boardService.getUpdatePost(postsTitle);
        model.addAttribute("postForm", postForm);
        return "board/update-post";
    }

    // 게시글 수정
    @PostMapping("/posts/{postsTitle}/update")
    public String updatePost(@PathVariable String postsTitle,
                             @ModelAttribute("postForm") PostForm postForm,
                             HttpServletRequest request) {
        boardService.updatePost(postsTitle, postForm, request);
        String title = postForm.getTitle();
        return "redirect:/posts/" + title;
    }

    // 게시글 삭제
    @PostMapping("/posts/{postsTitle}/delete")
    public String deletePost(@PathVariable String postsTitle, HttpServletRequest request) {
        boardService.deletePost(postsTitle, request);
        return "redirect:/";
    }
}
