package com.example.letsgongbu.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberStudyRoom> memberStudyRooms = new ArrayList<>();


    public Member(String username, String loginId, String password) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
    }
}
