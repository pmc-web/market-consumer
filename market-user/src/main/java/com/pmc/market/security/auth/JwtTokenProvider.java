package com.pmc.market.security.auth;

import com.pmc.market.entity.User;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    public static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간만 토큰 유효
    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7; // 1주

    @Value("${spring.jwt.secret:ThisIsA_SecretKeyForJwtExample123}")
    private String SECRET_KEY;

    @Resource(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    public String generateJwtAccessToken(User user) {
        return createToken(user.getEmail(), ACCESS_TOKEN_VALID_TIME);
    }

    public String generateJwtRefreshToken(User user) {
        return createToken(user.getEmail(), REFRESH_TOKEN_VALID_TIME);
    }

    private String createToken(String email, long expireDate) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("role", "USER");
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireDate))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    public Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime : {}, email : {}, role : {}", claims.getExpiration(), claims.get("email"), claims.get("role"));
            return true;
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Token Expired");
            throw new BusinessException("access Token 유효기간이 만료되었습니다, refresh token 을 요청 하거나 다시 로그인해주세요.", ErrorCode.TOKEN_EXPIRED);
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

    // email 로 인증 정보 조회
    public Authentication getAuthenticationLogin(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 파싱 : "Authorization" : jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(AuthConstants.AUTH_HEADER);
    }

}