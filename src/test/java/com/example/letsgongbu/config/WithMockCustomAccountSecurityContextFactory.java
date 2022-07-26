package com.example.letsgongbu.config;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.security.UserDetailsImpl;
import org.elasticsearch.common.collect.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WithMockCustomAccountSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomAccount> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomAccount annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // 2
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", annotation.username());
        attributes.put("loginId", annotation.loginId());
        attributes.put("password", annotation.password());


        Member member = new Member((String) attributes.get("username"),
                (String) attributes.get("loginId"),
                (String) attributes.get("password")
        );


        // 3
        UserDetailsImpl principal = new UserDetailsImpl(member);

        // 4
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                principal,
                principal.getAuthorities(),
                Collections.emptyList());

        // 5
        context.setAuthentication(token);
        return context;
    }
}
