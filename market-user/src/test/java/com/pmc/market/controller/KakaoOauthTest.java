package com.pmc.market.controller;

import com.pmc.market.UserApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class KakaoOauthTest {
    @Autowired
    MockMvc mockMvc;

    String REDIRECT = "https://kauth.kakao.com/oauth/authorize?" +
            "response_type=code&client_id=c1ba1f09ecd07ec2d41685d595cf8f15&scope=profile%20talk_message&state" +
            "=_f4nQxUr90T_w4VsgQSJb_bE6JKOm6Fno6CU5I-6aQc%3D&" +
            "redirect_uri=http://localhost:8086/login/oauth2/code/kakao";

    @Test
    void 카카오_로그인_회원가입() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/authorization/kakao")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }


}