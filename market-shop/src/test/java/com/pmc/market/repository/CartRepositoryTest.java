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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Transactional
    @DisplayName("생성")
    @Test
    void createCart() {
        User user = userRepository.findById(3L).get();
        Shop shop = user.getShops().get(0);
        Cart cart = Cart.builder()
                .regDate(LocalDateTime.now())
                .shop(shop)
                .user(user)
                .build();
    }

    @DisplayName("카트 찾기")
    @Test
    void findByUserIdAndShopId() {
        long userId = 3L;
        long shopId = 2L;
        Optional<Cart> cart = cartRepository.findByUserIdAndShopId(userId, shopId);
        assertTrue(cart.isPresent());
    }

    @DisplayName("유저의 카트 전체 정보")
    @Test
    void findByUserId() {
        long userId = 3L;
        List<Cart> carts = cartRepository.findByUserId(userId);
        assertTrue(carts.size() > 0);
    }

    @DisplayName("카드 삭제")
    @Test
    void deleteCart() {
        long cartId = 3L;
        cartRepository.deleteById(cartId);
    }
}