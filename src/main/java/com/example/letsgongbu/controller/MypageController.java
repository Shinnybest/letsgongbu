package com.example.letsgongbu.controller;


import com.example.letsgongbu.dto.response.MemberResponseDto;
import com.example.letsgongbu.dto.response.PostChange;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto;
import com.example.letsgongbu.service.BoardService;
import com.example.letsgongbu.service.CommentService;
import com.example.letsgongbu.service.MemberService;
import com.example.letsgongbu.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final StudyRoomService studyRoomService;

    @GetMapping("/my-page")
    public String getMyPage(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        MemberResponseDto.MemberName member = memberService.getMemberName(userDetails);
        model.addAttribute("member", member);
        return "my-page/my-page";
    }

    @GetMapping("/my-page/posts")
    public String getAllMyPosts(@AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        List<PostResponseDto> posts = boardService.findAllPostsByMe(userDetails.getUsername());
        model.addAttribute("posts", posts);
        return "my-page/my-posts";
    }

    @GetMapping("/my-page/posts/comments")
    public String getAllMyCommentsPosts(@AuthenticationPrincipal UserDetails userDetails,
                                        Model model) {
        List<PostResponseDto> posts = commentService.findAllPostsCommentedByMe(userDetails);
        model.addAttribute("posts", posts);
        return "my-page/my-comments-posts";
    }

    @GetMapping("/my-page/study-rooms")
    public String getAllMyStudyRooms(@AuthenticationPrincipal UserDetails userDetails,
                                     Model model) {
        List<StudyRoomResponseDto> studyRooms = studyRoomService.findMyStudyRooms(userDetails);
        model.addAttribute("studyRooms", studyRooms);
        return "my-page/my-study-rooms";
    }
}
