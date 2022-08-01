package com.example.letsgongbu.service.Impl;


import com.example.letsgongbu.domain.*;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.PostUpdateForm;
import com.example.letsgongbu.dto.response.PostAndCommentResponseDto;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.repository.BoardRepository;
import com.example.letsgongbu.repository.CommentRepository;
import com.example.letsgongbu.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    @InjectMocks
    BoardServiceImpl boardService;
    @Mock
    BoardRepository boardRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    CommentRepository commentRepository;

    @Test
    void findAllPosts() {
        //given
        Member member = new Member("username", "loginId", "password");
        List<Post> posts = new ArrayList<>();
        //1
        Post postOne = new Post(1L, "title1", "content1", MainCategory.ALGORITHMS, SubCategory.HEAP);
        postOne.setMember(member);
        posts.add(postOne);
        //2
        Post postTwo = new Post(2L, "title2", "content2", MainCategory.LANGUAGE, SubCategory.JAVA);
        postTwo.setMember(member);
        posts.add(postTwo);

        //stub
        when(boardRepository.findAll()).thenReturn(posts);

        //when
        List<PostResponseDto> dtos = boardService.findAllPosts();

        //then
        assertThat(2).isEqualTo(dtos.size());
        assertThat(1L).isEqualTo(dtos.get(0).getId());
        assertThat(2L).isEqualTo(dtos.get(1).getId());
    }

    @Test
    void uploadPost() {
        //given
        Member member = new Member("username", "loginId", "password");
        PostForm postForm = new PostForm("title", "content", MainCategory.CS, SubCategory.OS);
        Post post = new Post(1L, postForm.getTitle(), postForm.getContent(), postForm.getMainCategory(), postForm.getSubCategory());
        post.setMember(member);

        //stub
        when(boardRepository.save(any(Post.class))).thenReturn(post);
        when(memberRepository.findByUsername(member.getUsername())).thenReturn(Optional.of(member));

        //when
        Long postId = boardService.uploadPost(postForm, member.getUsername());

        //then
        assertThat(1L).isEqualTo(postId);
    }

    @Test
    void findPostAndComments() {
        // given
        Member member = new Member("username", "loginId", "password");
        Post post = new Post(100L, "title", "content", MainCategory.FRAMEWORK, SubCategory.DJANGO);
        post.setMember(member);
        Comment comment = new Comment(100L, "comment-content");
        comment.setMember(member);
        comment.setPost(post);

        // stub
        when(boardRepository.findById(100L)).thenReturn(Optional.of(post));
        when(memberRepository.findByUsername(member.getUsername())).thenReturn(Optional.of(member));

        // when
        PostAndCommentResponseDto dtoCorrect = boardService.findPostAndComments(100L, member.getUsername());

        // then
        assertThat(100L).isEqualTo(dtoCorrect.getId());
        assertThat(1).isEqualTo(dtoCorrect.getComments().size());
        assertThatThrownBy(() -> boardService.findPostAndComments(101L, member.getUsername())).isInstanceOf(CustomException.class);

    }

    @Test
    void updatePost() {
        //given
        Member member = new Member("username", "loginId", "password");
        Post post = new Post(100L, "title", "content", MainCategory.FRAMEWORK, SubCategory.DJANGO);
        post.setMember(member);
        PostForm postForm = new PostForm("title-2", "content-2", MainCategory.FRAMEWORK, SubCategory.DJANGO);

        //stub
        when(boardRepository.findByIdAndMember_Username(100L, member.getUsername())).thenReturn(Optional.of(post));

        //when
        boardService.updatePost(100L, postForm, member.getUsername());

        //then
        assertThat("title-2").isEqualTo(post.getTitle());
        assertThat("content-2").isEqualTo(post.getContent());
        assertThat(MainCategory.FRAMEWORK).isEqualTo(post.getMainCategory());
        assertThat(SubCategory.DJANGO).isEqualTo(post.getSubCategory());
    }

    @Test
    void deletePost() {
        //given
        Member member = new Member("username", "loginId", "password");
        Post post = new Post(100L, "title", "content", MainCategory.FRAMEWORK, SubCategory.DJANGO);
        post.setMember(member);
        boardRepository.save(post);
        Comment comment = new Comment(100L, "comment-content");
        comment.setMember(member);
        comment.setPost(post);
        commentRepository.save(comment);

        //stub
        when(boardRepository.findById(100L)).thenReturn(Optional.of(post));
        when(boardRepository.findByIdAndMember_Username(100L, member.getUsername())).thenReturn(Optional.of(post));

        //when
        boardService.deletePost(100L, member.getUsername());
    }

    @Test
    void getUpdatePost() {
        //given
        Member member = new Member("username", "loginId", "password");
        Post post = new Post(100L, "title", "content", MainCategory.FRAMEWORK, SubCategory.DJANGO);
        post.setMember(member);

        //stub
        when(memberRepository.findByUsername(member.getUsername())).thenReturn(Optional.of(member));
        when(boardRepository.findById(post.getId())).thenReturn(Optional.of(post));

        //when
        PostUpdateForm postForm = boardService.getUpdatePost(100L, member.getUsername());

        //then
        assertThat("title").isEqualTo(postForm.getTitle());
        assertThat("content").isEqualTo(postForm.getContent());
    }


    @Test
    void findAllPostsByMe() {
        //given
        Member member = new Member("username", "loginId", "password");
        List<Post> posts = new ArrayList<>();
        //1
        Post postOne = new Post(1L, "title1", "content1", MainCategory.ALGORITHMS, SubCategory.HEAP);
        postOne.setMember(member);
        posts.add(postOne);
        //2
        Post postTwo = new Post(2L, "title2", "content2", MainCategory.LANGUAGE, SubCategory.JAVA);
        postTwo.setMember(member);
        posts.add(postTwo);

        //stub
        when(memberRepository.findByUsername(member.getUsername())).thenReturn(Optional.of(member));
        when(boardRepository.findAllByMember_Username(member.getUsername())).thenReturn(posts);

        //when
        List<PostResponseDto> dtos = boardService.findAllPostsByMe(member.getUsername());

        //then
        assertThat(2).isEqualTo(dtos.size());
        assertThat(1L).isEqualTo(dtos.get(0).getId());
        assertThat(2L).isEqualTo(dtos.get(1).getId());
    }
}