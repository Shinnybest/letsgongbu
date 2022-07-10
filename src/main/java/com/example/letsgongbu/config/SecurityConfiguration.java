package com.example.letsgongbu.config;

import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final MemberRepository memberRepository;
    private final DataSource dataSource;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/", "/login", "/logout", "/posts", "/studyroom/all", "/sign-up")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login"))
                .rememberMe(rememberMe -> rememberMe
                        .key("rememberMeTest")
//                        .alwaysRemember(false)
                        .rememberMeServices(rememberMeServices(persistentTokenRepository()))
//                        .rememberMeParameter("remember-me")
//                        .rememberMeCookieName("remember-me")
//                        .rememberMeCookieDomain("http://localhost:8080")
//                .useSecureCookie() // https 사용
//                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(new UserDetailsServiceImpl(memberRepository))
//                .authenticationSuccessHandler()
//                        .tokenValiditySeconds(1000)
                        )
                .httpBasic().disable();
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public RememberMeServices rememberMeServices(PersistentTokenRepository tokenRepository){
        PersistentTokenBasedRememberMeServices rememberMeServices = new
                PersistentTokenBasedRememberMeServices("rememberMeTest", new UserDetailsServiceImpl(memberRepository), tokenRepository);
        rememberMeServices.setParameter("remember-me");
        rememberMeServices.setCookieName("remember-me");
        return rememberMeServices;
    }
}