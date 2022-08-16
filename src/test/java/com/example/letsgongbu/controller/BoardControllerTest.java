package com.example.letsgongbu.controller;


import com.example.letsgongbu.config.WithMockCustomAccount;
import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import com.example.letsgongbu.dto.request.PostUpdateForm;
import com.example.letsgongbu.dto.response.PostAndCommentResponseDto;
import com.example.letsgongbu.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BoardController.class})
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    @Autowired
    BoardService boardServiceMock;

    @Autowired
    WebApplicationContext was;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                        .webAppContextSetup(was)
                        .build();
    }


    @Test
    @DisplayName("GET : 전체 게시글 조회")
    void getPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/posts"));
    }

    @Test
    @DisplayName("GET : 검색 게시글 조회")
    @WithMockCustomAccount
    void getSearchPosts() throws Exception {
        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/posts"));
    }

    @Test
    @DisplayName("GET : 상세 게시글 조회")
    @WithMockCustomAccount
    void readPostPage() throws Exception {
        //given
        PostAndCommentResponseDto postAndComments = PostAndCommentResponseDto.builder()
                .id(1L)
                .title("title")
                .postContent("content")
                .postWriter("writer")
                .postCreatedAt(LocalDateTime.now())
                .mainCategory(MainCategory.CS)
                .subCategory(SubCategory.OS)
                .commentCnt(1)
                .comments(Collections.emptyList())
                .build();

        when(boardServiceMock.findPostAndComments(1L, SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(postAndComments);

        //when, then
        mockMvc.perform(get("/posts/{postId}", 1))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("GET : 게시글 작성 페이지 조회")
    @WithMockCustomAccount
    void getPostPage() throws Exception {
        mockMvc.perform(get("/post/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/upload-post"));
    }

    @Test
    @DisplayName("POST : 게시글 업로드")
    @WithMockCustomAccount
    void uploadPost() throws Exception {
        mockMvc.perform(post("/post"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("GET : 게시글 수정 페이지 조회")
    @WithMockCustomAccount
    void getUpdatePost() throws Exception {
        //given
        PostUpdateForm postUpdateForm = PostUpdateForm.builder()
                .id(1L)
                .title("title")
                .content("content")
                .mainCategory(MainCategory.CS)
                .subCategory(SubCategory.OS)
                .build();

        when(boardServiceMock.getUpdatePost(1L, SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(postUpdateForm);

        // then
        mockMvc.perform(get("/posts/{postId}/update", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("board/update-post"));
    }

    @Test
    @DisplayName("POST : 게시글 수정")
    @WithMockCustomAccount
    void updatePost() throws Exception {
        mockMvc.perform(post("/posts/1/update"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("POST : 게시글 삭제")
    @WithMockCustomAccount
    void deletePost() throws Exception {
        mockMvc.perform(post("/posts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/posts"));
    }
}