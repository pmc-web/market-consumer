package com.pmc.market.controller;

import com.pmc.market.UserApplication;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.model.user.entity.Role;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @Autowired
    private UserService userService;

    @WithMockUser
    @DisplayName("판매자로 전환한다_임시용")
    @Test
    void changeUserRole() throws Exception {
        User user = User.builder()
                .id(1L)
                .role(Role.BUYER)
                .regDate(LocalDateTime.now())
                .email("annna0449@naver.com")
                .build();
        doNothing().when(userServiceMock).changeToSeller(user.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/users/role"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @DisplayName("닉네임 체크_ 중복")
    @Test
    void checkNickname_error() throws Exception {
        String nickname = "70d4d35d";
        doThrow(new BusinessException(ErrorCode.DUPLICATE_ENTITY)).when(userService).checkUserNickname(nickname);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/nickname")
                .param("nickname", nickname))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser
    @DisplayName("닉네임 체크")
    @Test
    void checkNickname() throws Exception {
        String nickname = "nickname";
        when(userServiceMock.checkUserNickname(nickname)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/nickname")
                .param("nickname", nickname))
                .andExpect(status().isOk());
    }
}