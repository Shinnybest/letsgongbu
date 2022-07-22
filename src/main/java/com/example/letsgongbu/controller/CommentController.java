package com.example.letsgongbu.controller;

import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{postId}")
    public String uploadComment(@PathVariable Long postId,
                                @ModelAttribute("commentForm") CommentForm commentForm,
                                @AuthenticationPrincipal UserDetails userDetails) {
        commentService.saveComment(postId, commentForm, userDetails);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/comment/{postId}/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId, userDetails);
        return "redirect:/posts/" + postId;
    }

}
