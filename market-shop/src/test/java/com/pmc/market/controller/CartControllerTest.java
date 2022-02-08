package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.UserApplication;
import com.pmc.market.domain.shop.dto.CartProductRequestDto;
import com.pmc.market.domain.shop.dto.CartResponseDto;
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

import static org.mockito.Mockito.doNothing;
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
    void addToCart() throws Exception {
        CartProductRequestDto requestDto = CartProductRequestDto.builder()
                .shopId(2L)
                .productId(3L)
                .quantity(1)
                .size("1")
                .color("로얄블루")
                .build();
        long userId = 3L;
        doNothing().when(cartService).addToCart(userId, requestDto);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/carts/{userId}", userId)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("장바구니 물건 삭제")
    @Test
    void deleteToCart() throws Exception {
        long cartId = 3L;
        long cardProductId = 3L;
        doNothing().when(cartService).deleteProductToCart(cartId, cardProductId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/{cartId}/product", cartId)
                .param("cartProductId", String.valueOf(cardProductId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @WithMockUser
    @DisplayName("장바구니 업데이트")
    @Test
    void updateCartProduct() throws Exception {
        long cartId = 3L;
        CartProductRequestDto requestDto = CartProductRequestDto
                .builder()
                .quantity(2)
                .size("1")
                .shopId(3L)
                .productId(3L)
                .build();
        doNothing().when(cartService).updateCartProduct(cartId, requestDto);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/carts/{cartId}/product", cartId)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("장바구니 삭제")
    @Test
    void deleteCart() throws Exception {
        long cartId = 3L;
        doNothing().when(cartService).deleteCart(cartId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/{cartId}", cartId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}