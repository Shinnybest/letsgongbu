package com.example.letsgongbu.controller;

import com.example.letsgongbu.dto.request.CommentForm;
import com.example.letsgongbu.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{postTitle}")
    public String uploadComment(@PathVariable String postTitle, @ModelAttribute("commentForm") CommentForm commentForm, HttpServletRequest request) {
        commentService.saveComment(postTitle, commentForm, request);
        String referer = request.getHeader("referer");
        return "redirect:/posts/" + postTitle;
    }

    @PostMapping("/comment/{postTitle}/{commentsId}/delete")
    public String deleteComment(@PathVariable String postTitle, @PathVariable Long commentsId, HttpServletRequest request) {
        commentService.deleteComment(commentsId);
        String referer = request.getHeader("referer");
        return "redirect:/posts/" + postTitle;
    }

}
