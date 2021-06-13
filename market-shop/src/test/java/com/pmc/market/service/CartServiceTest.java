package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.CartProductRequestDto;
import com.pmc.market.model.dto.CartResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class CartServiceTest {

    @Autowired
    CartServiceImpl cartService;

    @DisplayName("전체 장바구니 ")
    @Test
    void getUserCarts() {
        long userId = 3L;
        List<CartResponseDto> list = cartService.getUserCarts(userId);
        list.stream().forEach(l -> {
            l.getProducts().stream().forEach(p -> System.out.print(p.getProductId() + " " + p.getProductName()));
            System.out.println();
        });
        assertTrue(list.size() > 0);
    }

    @DisplayName("마켓에 대한 장바구니")
    @Test
    void getUserCartByShop() {
        long userId = 3L;
        long shopId = 2L;
        CartResponseDto cartResponseDto = cartService.getUserCartByShop(userId, shopId);
        assertEquals(cartResponseDto.getShopId(), shopId);
    }

    @DisplayName("장바구니에 물건 담기")
    @Test
    void addToCart() {
        CartProductRequestDto requestDto = CartProductRequestDto.builder()
                .productId(5L)
                .shopId(4L)
                .quantity(1)
                .totalPrice(8000)
                .color("마젠타")
                .build();
        long userId = 3L;
        cartService.addToCart(userId, requestDto);
    }

    @Test
    void deleteCart() {
        long cartId = 3L;
        cartService.deleteCart(cartId);
    }

    @Test
    void deleteProductToCart() {
        long cartProductId = 6L;
        long cartId = 2L;
        cartService.deleteProductToCart(cartId, cartProductId);
    }

    @Test
    void updateCartProduct() {
        long cartId = 5L;
        CartProductRequestDto requestDto = CartProductRequestDto.builder()
                .color("블루")
                .quantity(2)
                .totalPrice(16000)
                .size("1")
                .build();
        cartService.updateCartProduct(cartId, requestDto);
    }
}