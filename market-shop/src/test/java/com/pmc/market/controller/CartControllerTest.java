package com.pmc.market.controller;

import com.pmc.market.UserApplication;
import com.pmc.market.model.dto.CartResponseDto;
import com.pmc.market.service.CartService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartService cartService;

    @WithMockUser
    @DisplayName("내 전체 장바구니")
    @Test
    void getUserCarts() throws Exception {
        long userId = 3L;
        List<CartResponseDto> list = new ArrayList<>();
        list.add(CartResponseDto.builder().build());
        when(cartService.getUserCarts(userId)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("마켓의 장바구니")
    @Test
    void getShopCart() throws Exception {
        long userId = 3L;
        long shopId = 2L;
        CartResponseDto cart = CartResponseDto.builder().build();
        when(cartService.getUserCartByShop(userId, shopId)).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/{userId}/shop", userId)
                .param("shopId", String.valueOf(shopId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("장바구니에 물건 추가")
    @Test
    void addToCart() {

    }

    @WithMockUser
    @DisplayName("장바구니 물건 삭제")
    @Test
    void deleteToCart() {

    }

    @WithMockUser
    @DisplayName("장바구니 업데이트")
    @Test
    void updateCartProduct() {

    }

    @WithMockUser
    @DisplayName("장바구니 삭제")
    @Test
    void deleteCart() {

    }
}