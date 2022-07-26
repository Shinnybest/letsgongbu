package com.example.letsgongbu.controller;


import com.example.letsgongbu.config.WithMockCustomAccount;
import com.example.letsgongbu.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    @Autowired
    CommentService commentServiceMock;

    @Autowired
    WebApplicationContext was;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(was)
                .build();
    }

    @Test
    @DisplayName("POST : 댓글 업로드")
    @WithMockCustomAccount
    void uploadComment() throws Exception {
        mockMvc.perform(post("/comment/{postId}", 1))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("POST : 댓글 삭제")
    @WithMockCustomAccount
    void deleteComment() throws Exception {
        mockMvc.perform(post("/comment/{postId}/{commentId}/delete", 1, 1))
                .andExpect(status().is3xxRedirection());
    }
}