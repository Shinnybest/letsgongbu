package com.example.letsgongbu.controller;


import com.example.letsgongbu.config.WithMockCustomAccount;
import com.example.letsgongbu.domain.MainCategory;
import com.example.letsgongbu.domain.SubCategory;
import com.example.letsgongbu.dto.response.MemberResponseDto;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.dto.response.StudyRoomResponseDto;
import com.example.letsgongbu.service.BoardService;
import com.example.letsgongbu.service.CommentService;
import com.example.letsgongbu.service.MemberService;
import com.example.letsgongbu.service.StudyRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = {MyPageController.class})
class MyPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    @Autowired
    CommentService commentServiceMock;

    @MockBean
    @Autowired
    MemberService memberServiceMock;

    @MockBean
    @Autowired
    BoardService boardServiceMock;

    @MockBean
    @Autowired
    StudyRoomService studyRoomServiceMock;

    @Autowired
    WebApplicationContext was;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(was)
                .build();
    }

    @Test
    @DisplayName("GET : 마이페이지 전체 불러오기")
    @WithMockCustomAccount
    void getMyPage() throws Exception {
        // given
        MemberResponseDto.MemberName username = new MemberResponseDto.MemberName("username");
        // when
        when(memberServiceMock.getMemberName((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .thenReturn(username);
        // then
        mockMvc.perform(get("/my-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-page/my-page"))
                .andReturn();
    }

    @Test
    @DisplayName("GET : 내가 쓴 글 전체 조회")
    @WithMockCustomAccount
    void getAllMyPosts() throws Exception {
        // given
        List<PostResponseDto> posts = new ArrayList<>();
        for (int i=1; i<3; i++) {
            Long id = (long) i;
            PostResponseDto newDto = PostResponseDto.builder()
                    .id(id)
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .commentCnt(1)
                    .createdAt(LocalDateTime.now())
                    .mainCategory(MainCategory.CS)
                    .subCategory(SubCategory.DJANGO)
                    .build();
            posts.add(newDto);
        }
        // when
        when(boardServiceMock.findAllPostsByMe(SecurityContextHolder.getContext().getAuthentication().getName()))
                .thenReturn(posts);
        // then
        mockMvc.perform(get("/my-page/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-page/my-posts"))
                .andReturn();
    }

    @Test
    @DisplayName("GET : 내가 댓글 쓴 글 전체 조회")
    @WithMockCustomAccount
    void getAllMyCommentsPosts() throws Exception {
        // given
        List<PostResponseDto> posts = new ArrayList<>();
        for (int i=1; i<3; i++) {
            Long id = (long) i;
            PostResponseDto newDto = PostResponseDto.builder()
                    .id(id)
                    .title("title")
                    .content("content")
                    .writer("writer")
                    .commentCnt(1)
                    .createdAt(LocalDateTime.now())
                    .mainCategory(MainCategory.CS)
                    .subCategory(SubCategory.DJANGO)
                    .build();
            posts.add(newDto);
        }
        // when
        when(commentServiceMock.findAllPostsCommentedByMe((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .thenReturn(posts);
        // then
        mockMvc.perform(get("/my-page/posts/comments"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-page/my-comments-posts"))
                .andReturn();
    }

    @Test
    @DisplayName("GET : 참여중인 스터디룸 전체 조회")
    @WithMockCustomAccount
    void getAllMyStudyRooms() throws Exception {
        // given
        List<StudyRoomResponseDto> studyRooms = new ArrayList<>();
        for (int i=1; i<3; i++) {
            Long id = (long) i;
            StudyRoomResponseDto newDto = StudyRoomResponseDto.builder()
                    .id(id)
                    .title("title")
                    .mainCategory(MainCategory.CS)
                    .subCategory(SubCategory.DJANGO)
                    .startDay(Date.valueOf("2022-02-05"))
                    .endDay(Date.valueOf("2022-03-05"))
                    .imgUrl("bts.img")
                    .build();
            studyRooms.add(newDto);
        }
        // when
        when(studyRoomServiceMock.findMyStudyRooms((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .thenReturn(studyRooms);
        // then
        mockMvc.perform(get("/my-page/study-rooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-page/my-study-rooms"))
                .andReturn();
    }
}