package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.CartResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

    }

    @Test
    void addToCart() {
    }

    @Test
    void deleteCart() {
    }

    @Test
    void deleteProductToCart() {
    }

    @Test
    void updateCartProduct() {
    }
}