package com.example.letsgongbu.domain;

import com.example.letsgongbu.dto.request.PostForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class Post extends TimeStamped {

    @Id @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;

    @Column
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "post")
    List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String content, MainCategory mainCategory, SubCategory subCategory) {
        this.title = title;
        this.content = content;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void updatePost(PostForm postForm) {
        this.title = postForm.getTitle();
        this.content = postForm.getContent();
        this.mainCategory = postForm.getMainCategory();
        this.subCategory = postForm.getSubCategory();
    }

    public Post(Long id, String title, String content, MainCategory mainCategory, SubCategory subCategory) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }
}
