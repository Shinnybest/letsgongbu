package com.example.letsgongbu.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.PatternUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static String[] whitelist = {"/", "/login", "/sign-up", "/logout"};

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작, {}", requestURI);
            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행, {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                Cookie[] cookies = httpRequest.getCookies();
                Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                        .filter(cookie -> cookie.getValue().equals(session.getId()))
                        .findAny();
                Cookie cookie = cookieOptional.get();
                if (!Objects.equals(cookie.getName(), "loginCookie") & !Objects.equals(cookie.getName(), "JSESSIONID")) {
                    log.info("미인증 사용자의 요청, {}", requestURI);
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료, {}", requestURI);
        }

    }

    public boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
