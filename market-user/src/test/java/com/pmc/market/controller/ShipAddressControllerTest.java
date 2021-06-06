package com.pmc.market.controller;

import com.pmc.market.UserApplication;
import com.pmc.market.service.ShipAddressService;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ShipAddressControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShipAddressService shipAddressService;

    @WithMockUser
    @DisplayName("배송지 추가")
    @Test
    void addShipAddress() {
    }

    @WithMockUser
    @DisplayName("배송지 수정")
    @Test
    void updateShipAddress() {
    }

    @WithMockUser
    @DisplayName("유저 배송지 목록")
    @Test
    void getShipAddress() {
    }

    @WithMockUser
    @DisplayName("배송지 삭제")
    @Test
    void deleteShipAddress() {
    }
}