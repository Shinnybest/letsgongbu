package com.example.letsgongbu.controller;


import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.dto.response.MemberResponseDto;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.service.BoardService;
import com.example.letsgongbu.service.CommentService;
import com.example.letsgongbu.service.MemberService;
import com.example.letsgongbu.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/mypage")
    public String getMyPage(HttpServletRequest request, Model model) {
        MemberResponseDto.MemberName dto = memberService.getMemberName(request);
        model.addAttribute("member", dto);
        return "/my-page/my-page";
    }

    @GetMapping("/mypage/posts/{username}")
    public String getAllMyPosts(@PathVariable String username, Model model) {
        List<PostResponseDto.PostList> dto = boardService.findAllPostsByMe(username);
        model.addAttribute("posts", dto);
        return "/my-page/my-posts";
    }

    @GetMapping("/mypage/posts/comments/{username}")
    public String getAllMyCommentsPosts(@PathVariable String username, Model model) {
        List<PostResponseDto.PostList> dto = commentService.findAllPostsCommentedByMe(username);
        model.addAttribute("posts", dto);
        return "/my-page/my-comments-posts";
    }

    @GetMapping("/mypage/studyrooms/{username}")
    public String getAllMyStudyRooms(@PathVariable String username, Model model) {
        return "/my-page/my-studyrooms";
    }


}
