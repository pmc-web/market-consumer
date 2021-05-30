package com.pmc.market.security.auth;

import com.pmc.market.entity.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    private static final String secretKey = "ThisIsA_SecretKeyForJwtExample123#";

    private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효

    @Resource(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    public String generateJwtToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes());
        return builder.compact();
    }

    public Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Date createExpireDateForOneYear() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    private Map<String, Object> createClaims(User user) {
        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("role", "USER"); // role 설정

        return claims;
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime :" + claims.getExpiration());
            log.info("email :" + claims.get("email"));
            log.info("role :" + claims.get("role"));
            return true;

        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("{} Token Tampered", exception.getMessage());
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFormToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(claims.get("email")));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Authentication getAuthenticationLogin(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 파싱 : "Authorization" : jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(AuthConstants.AUTH_HEADER);
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}