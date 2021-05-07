package com.pmc.market.security.auth;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.User;
import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {

    private static final String secretKey = "ThisIsA_SecretKeyForJwtExample123#";

    public static String generateJwtToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(createExpireDateForOneYear())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes());

        getClaimsFormToken(builder.compact());

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
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

    public static String getTokenFromHeader(String header) {
         return header.split(" ")[1];
    }

    private static Date createExpireDateForOneYear() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(User user) {
        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());

        return claims;
    }

    public static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
    }

    private String resolveToken(HttpServletRequest req){
        return req.getHeader("X-AUTH-TOKEN");
    }

    private boolean validateToken(String token){
        return false;
    }

    private static String getUserEmailFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("email");
    }

    private static Role getRoleFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (Role) claims.get("role");
    }
}