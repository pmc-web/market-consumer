package com.pmc.market.security.auth;

import com.pmc.market.UserApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void getData() {
    }

    @Test
    void setData() {
    }

    @Test
    void setDataExpire() {
        String value = "jwttoken";
        redisUtil.setDataExpire(UUID.randomUUID().toString(), value, 20);
    }

}