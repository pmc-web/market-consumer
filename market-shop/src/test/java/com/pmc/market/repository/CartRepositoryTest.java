package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.shop.repository.CartRepository;
import com.pmc.market.domain.shop.repository.ShopRepository;
import com.pmc.market.domain.user.entity.Cart;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ShopRepository shopRepository;

    private User testUser;
    private Shop testShop;
    private Cart savedCart;

    @BeforeEach
    void setup() {
        testUser = userRepository.save(UserTest.createUser());
        testShop = shopRepository.save(ShopTest.createTestShop(testUser, null));
        Cart cart = Cart.builder()
                .shop(testShop)
                .user(testUser)
                .build();
        savedCart = cartRepository.save(cart);
    }


    @DisplayName("유저와 마켓 정보로 카드를 생성한다.")
    @Test
    void createCart() {
        Optional<Cart> findCart = cartRepository.findByUser_IdAndShop_Id(testUser.getId(), testShop.getId());
        assertThat(savedCart).isEqualTo(findCart.get());
    }

    @DisplayName("유저의 카트 전체 정보를 업데이트 시간 순으로 가져온다.")
    @Test
    void findByUserId() {
        List<Cart> carts = cartRepository.findByUser_IdOrderByUpdatedDateDesc(testUser.getId());
        assertTrue(carts.size() > 0);
    }

    @DisplayName("카트를 삭제할 때 없는 id여도 exception이 발생하지 않는다.")
    @Test
    void deleteCartNotExistId() {
        assertDoesNotThrow(() -> cartRepository.deleteById(12312434l));
    }

    @Test
    void deleteCart() {
        cartRepository.deleteById(savedCart.getId());
        Optional<Cart> cart = cartRepository.findByUser_IdAndShop_Id(testUser.getId(), testShop.getId());
        assertThat(cart.isPresent()).isFalse();
    }
}