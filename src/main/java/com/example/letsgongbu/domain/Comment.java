package com.example.letsgongbu.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Comment(String content) {
        this.content = content;
    }

    public Comment(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }
}
