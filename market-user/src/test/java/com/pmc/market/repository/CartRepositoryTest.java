package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.Cart;
import com.pmc.market.model.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @DisplayName("생성")
    @Test
    void createCart() {
        String email = "annna0449@naver.com";
        User user = userRepository.findByEmail(email).get();
        Shop shop = Shop.builder()
                .build();

        Cart cart = Cart.builder()
                .regDate(LocalDateTime.now())
                .shop(shop)
                .user(user)
                .build();
    }
}