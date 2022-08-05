package com.example.letsgongbu.repository;

import com.example.letsgongbu.domain.*;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("ID로 게시글 찾기")
    void findById() {
        // given
        Post post1 = boardRepository.save(new Post("title1", "content1", MainCategory.FRAMEWORK, SubCategory.DJANGO));
        Post post2 = boardRepository.save(new Post("title2", "content2", MainCategory.LANGUAGE, SubCategory.PYTHON));
        // when
        Post foundPost1 = boardRepository.findById(post1.getId())
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        Post foundPost2 = boardRepository.findById(post2.getId())
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        // then
        assertThat(post1.getTitle()).isEqualTo(foundPost1.getTitle());
        assertThat(post2.getTitle()).isEqualTo(foundPost2.getTitle());
    }

    @Test
    @DisplayName("ID & 게시글 작성자 일치여부 확인")
    void findByIdAndMember_Username() {
        // given
        Member member = new Member("username", "loginId", "password");
        memberRepository.save(member);
        Post post = new Post("title1", "content1", MainCategory.FRAMEWORK, SubCategory.DJANGO);
        post.setMember(member);
        Post savedPost = boardRepository.save(post);
        // when
        Post foundPost = boardRepository.findByIdAndMember_Username(savedPost.getId(), "username")
                .orElseThrow(() -> new CustomException(Error.NO_AUTHORITY_POST));
        // then
        assertThat(post.getTitle()).isEqualTo(foundPost.getTitle());
        assertThatThrownBy(() -> boardRepository.findByIdAndMember_Username(savedPost.getId(), "wrong-username")
                .orElseThrow(() -> new CustomException(Error.NO_AUTHORITY_POST)))
                .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("해당 유저가 작성한 게시글 모두 찾기")
    void findAllByMember_Username() {
        // given
        Post post = new Post("title1", "content1", MainCategory.FRAMEWORK, SubCategory.DJANGO);
        Member member = new Member("username", "loginId", "password");
        memberRepository.save(member);
        post.setMember(member);
        boardRepository.save(post);
        // when
        List<Post> postsByUsername = boardRepository.findAllByMember_Username("username");
        // then
        assertThat(post.getTitle()).isEqualTo(postsByUsername.get(0).getTitle());
    }

    @Test
    @DisplayName("해당 멤버가 댓글 남긴 게시글 모두 찾기")
    void findAllByCommentByMe() {
        // given
        Post post = boardRepository.save(new Post("title1", "content1", MainCategory.FRAMEWORK, SubCategory.DJANGO));
        boardRepository.save(new Post("title2", "content2", MainCategory.LANGUAGE, SubCategory.PYTHON));
        Comment comment = new Comment("content");
        Member member = new Member("username", "loginId", "password");
        memberRepository.save(member);
        comment.setMember(member);
        comment.setPost(post);
        commentRepository.save(comment);
        // when
        List<Post> postsCommentedByUsername = boardRepository.findAllByCommentByMe("username");
        // then
        assertThat(post.getTitle()).isEqualTo(postsCommentedByUsername.get(0).getTitle());
    }
}