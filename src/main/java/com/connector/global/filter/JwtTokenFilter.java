package com.connector.global.filter;

import com.connector.global.token.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final TokenManager tokenManager;
    private final static String AUTHORIZATION_HEADER = "x-auth-token";

    @Value("${except-uri}")
    private String exceptURI;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, NullPointerException {

        // 1) 토큰 조회
        String accessToken = this.resolveToken(request);

        // 2) 토큰 벨리데이션
        tokenManager.validateToken(accessToken);

        filterChain.doFilter(request, response);
    }

    /**
     * HTTP Header > bearerToken 추출
     *
     * @param request
     */
    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        if (new AntPathRequestMatcher("/api/profile", HttpMethod.GET.toString()).matches(request)) {
            return true;
        }

        if (new AntPathRequestMatcher("/api/profile/user/**", HttpMethod.GET.toString()).matches(request)) {
            return true;
        }

        if (new AntPathRequestMatcher("/api/profile/github/**", HttpMethod.GET.toString()).matches(request)) {
            return true;
        }

        if (new AntPathRequestMatcher("/api/auth", HttpMethod.POST.toString()).matches(request)) {
            return true;
        }

        if (new AntPathRequestMatcher("/api/users", HttpMethod.POST.toString()).matches(request)) {
            return true;
        }

        return Arrays.asList(exceptURI.split(",").clone())
                .stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
    }
}
