package com.pmc.market.config;

import com.pmc.market.entity.User;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.security.auth.JwtTokenProvider;
import com.pmc.market.security.auth.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final RedisUtil redisUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String refreshToken = jwtTokenProvider.getRefreshToken((HttpServletRequest) request);
        if (token != null) {
            token = jwtTokenProvider.getTokenFromHeader(token);
            // 유효한 access token
            if (jwtTokenProvider.isValidToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else if (refreshToken != null) {
                // access token 이 기간 만료되면 refresh token 인증
                String userEmail = redisUtil.getData(refreshToken);
                Authentication auth = jwtTokenProvider.getAuthentication(refreshToken);
                CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
                User user = userDetails.getUser();
                if (userEmail.equals(user.getEmail())) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        chain.doFilter(request, response);
    }
}