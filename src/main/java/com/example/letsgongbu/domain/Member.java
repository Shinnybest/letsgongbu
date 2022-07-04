package com.example.letsgongbu.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String loginId;

    @Column
    private String password;

    @Column
    private String sessionId;

    @Column
    private LocalDate sessionLimit;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    public Member(String username, String loginId, String password, String sessionId, LocalDate sessionLimit) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.sessionId = sessionId;
        this.sessionLimit = sessionLimit;
    }

    public void recordSessionId(String sessionId, LocalDate sessionLimit) {
        this.sessionId = sessionId;
        this.sessionLimit = sessionLimit;
    }
}
