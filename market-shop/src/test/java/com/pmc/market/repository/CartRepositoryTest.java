package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.shop.repository.CartRepository;
import com.pmc.market.domain.user.entity.Cart;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
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
                .shop(shop)
                .user(user)
                .build();
    }

    @DisplayName("카트 찾기")
    @Test
    void findByUserIdAndShopId() {
        long userId = 1L;
        long shopId = 1L;
        Optional<Cart> cart = cartRepository.findByUser_IdAndShop_Id(userId, shopId);
        assertTrue(cart.isPresent());
    }

    @DisplayName("유저의 카트 전체 정보")
    @Test
    void findByUserId() {
        long userId = 1L;
        List<Cart> carts = cartRepository.findByUser_IdOrderByCreatedDateDesc(userId);
        assertTrue(carts.size() > 0);
    }

    @DisplayName("카드 삭제")
    @Test
    void deleteCart() {
        long cartId = 3L;
        cartRepository.deleteById(cartId);
    }
}