package com.example.letsgongbu.controller;


import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.PostUpdateForm;
import com.example.letsgongbu.dto.request.SearchForm;
import com.example.letsgongbu.dto.response.PostAndCommentResponseDto;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.security.UserDetailsImpl;
import com.example.letsgongbu.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    // 전체 게시글 조회
    @GetMapping("/posts")
    public String getPosts(@ModelAttribute("searchForm") SearchForm searchForm,
                           Model model) {
        List<PostResponseDto> posts = boardService.findAllPosts();
        model.addAttribute("posts", posts);
        return "board/posts";
    }

    // 검색하기
    @GetMapping("/search")
    public String getSearchPosts(@ModelAttribute("searchForm") SearchForm searchForm,
                                 Pageable p,
                                 Model model) {
        List<PostResponseDto> posts = boardService.findSearchPosts(searchForm.getWord(), p);
        model.addAttribute("posts", posts);
        return "board/posts";
    }

    // 상세 게시글 보기
    @GetMapping("/posts/{postId}")
    public String readPostPage(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable Long postId,
                             Model model,
                             @ModelAttribute("commentForm") CommentForm commentForm) {
        PostAndCommentResponseDto postAndComments= boardService.findPostAndComments(postId, userDetails.getUsername());
        model.addAttribute("postAndComments", postAndComments);
        return "board/read-post";
    }

    // 게시글 작성 페이지
    @GetMapping("/post/new")
    public String getPostPage(
            @AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute PostForm postForm) {
        return "board/upload-post";
    }

    // 게시글 작성하기
    @PostMapping("/post")
    public String uploadPost(@ModelAttribute("postForm") PostForm postForm,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Long postId = boardService.uploadPost(postForm, userDetails.getUsername());
        return "redirect:/posts/" + postId;
    }

    // 게시글 수정 페이지
    @GetMapping("/posts/{postId}/update")
    public String getUpdatePost(@PathVariable Long postId,
                                Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {
        PostUpdateForm postUpdateForm = boardService.getUpdatePost(postId, userDetails.getUsername());
        model.addAttribute("postUpdateForm", postUpdateForm);
        return "board/update-post";
    }

    // 게시글 수정
    @PostMapping("/posts/{postId}/update")
    public String updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable Long postId,
                             @ModelAttribute("postForm") PostForm postForm) {
        boardService.updatePost(postId, postForm, userDetails.getUsername());
        return "redirect:/posts/" + postId;
    }

    // 게시글 삭제
    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable Long postId) {
        boardService.deletePost(postId, userDetails.getUsername());
        return "redirect:/posts";
    }
}
