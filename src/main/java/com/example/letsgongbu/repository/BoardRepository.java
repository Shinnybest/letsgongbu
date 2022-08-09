package com.example.letsgongbu.repository;

import com.example.letsgongbu.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);
    Optional<Post> findByIdAndMember_Username(Long id, String username);
    List<Post> findAllByMember_Username(String username);
    @Query("select distinct p from Post p join fetch p.comments c where c.member.username = :username")
    List<Post> findAllByCommentByMe(String username);
}
