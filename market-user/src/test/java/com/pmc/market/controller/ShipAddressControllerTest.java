package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.UserApplication;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.ShipAddressRequestDto;
import com.pmc.market.model.dto.ShipAddressResponseDto;
import com.pmc.market.model.user.entity.ShipAddress;
import com.pmc.market.service.ShipAddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ShipAddressControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShipAddressService shipAddressService;

    ShipAddressRequestDto requestDto = ShipAddressRequestDto.builder()
            .address("서울 광진구 화양동")
            .detail("302호")
            .zipCode("421323")
            .build();

    @WithMockUser
    @DisplayName("배송지 추가")
    @Test
    void addShipAddress() throws Exception {
        long userId = 1L;
        doNothing().when(shipAddressService).addShipAddress(userId, requestDto);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/address", userId)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("배송지 수정")
    @Test
    void updateShipAddress() throws Exception {
        long userId = 1L;
        long addressId = 1L;
        doNothing().when(shipAddressService).updateShipAddress(userId, requestDto);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/address/{addressId}", userId, addressId)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("유저 배송지 목록")
    @Test
    void getShipAddress() throws Exception {
        long userId = 1L;

        List<ShipAddressResponseDto> addressResponseDtoList = new ArrayList<>();
        addressResponseDtoList.add(
                ShipAddressResponseDto.from(ShipAddress.builder()
                        .zipCode("1212345")
                        .detail("detail")
                        .address("address")
                        .addressName("addressName")
                        .build()));
        when(shipAddressService.getShipAddressList(userId)).thenReturn(addressResponseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/address", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("배송지 삭제")
    @Test
    void deleteShipAddress() throws Exception {
        long userId = 1L;
        long addressId = 1L;
        doNothing().when(shipAddressService).deleteShipAddress(userId, addressId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}/address/{addressId}", userId, addressId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("기본 배송지 변경")
    @Test
    void setDefaultAddress() throws Exception {
        long userId = 1L;
        long addressId = 2L;
        when(shipAddressService.setDefaultAddress(userId, addressId)).thenReturn(ResponseMessage.success());
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/{userId}/address/{addressId}/default", userId, addressId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}