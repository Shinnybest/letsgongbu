package com.example.letsgongbu.domain;


import com.example.letsgongbu.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TimeStampedTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void created() {

        // given
        LocalDateTime now = LocalDateTime.now();
        boardRepository.save(Post.builder()
                                .title("제목1")
                                .content("내용1")
                                .mainCategory(MainCategory.CS)
                                .subCategory(SubCategory.OS)
                                .build());

        //when
        List<Post> posts = boardRepository.findAll();
        Post post = posts.get(0);

        //then
        Assertions.assertThat(post.getCreatedAt()).isAfter(now);
        Assertions.assertThat(post.getLastModifiedAt()).isAfter(now);
    }

}