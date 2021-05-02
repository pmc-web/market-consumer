package com.pmc.market.security.auth;

import com.pmc.market.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenUtilsTest {

    @Test
    void token() {
        String token = TokenUtils.generateJwtToken(User.builder()
                .email("aaaa@naever.com").password("asdfa123##").build());

        Claims claims = TokenUtils.getClaimsFormToken(token);

        System.out.println(token +" "+ claims.getSubject());
    }

}