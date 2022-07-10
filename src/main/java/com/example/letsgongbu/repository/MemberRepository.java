package com.example.letsgongbu.repository;

import com.example.letsgongbu.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByCookieValue(String sessionId);
    Optional<Member> findByUsername(String username);

}
