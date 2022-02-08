package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.domain.product.entity.Product;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.shop.repository.CartProductRepository;
import com.pmc.market.domain.shop.repository.CartRepository;
import com.pmc.market.domain.shop.repository.ShopRepository;
import com.pmc.market.domain.user.entity.Cart;
import com.pmc.market.domain.user.entity.CartProduct;
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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;

    private User testUser;
    private Product testProduct;
    private Cart savedCart;

    @BeforeEach
    void setup() {
        testUser = userRepository.save(UserTest.createUser());
        Product product = Product.builder()
                .name("testProduct")
                .price(10000)
                .amount(100)
                .size("M")
                .color("blue gray")
                .build();
        Shop testShop = shopRepository.save(ShopTest.createTestShop(testUser, null));
        savedCart = cartRepository.save(Cart.builder().user(testUser).shop(testShop).build());
        testProduct = productRepository.save(product);
    }

    @DisplayName("장바구니에 물건 추가")
    @Test
    void createCartProduct() {
        int quantity = 2;
        CartProduct cartProduct = CartProduct.builder()
                .product(testProduct)
                .cart(savedCart)
                .quantity(quantity)
                .totalPrice(testProduct.getPrice() * quantity)
                .size(testProduct.getSize())
                .color(testProduct.getColor())
                .build();
        CartProduct save = cartProductRepository.save(cartProduct);
        assertThat(save).isEqualTo(cartProduct);
    }

    @DisplayName("장바구니에서 물건 삭제")
    @Test
    void cartProductDelete() {
        int quantity = 2;
        CartProduct cartProduct = CartProduct.builder()
                .product(testProduct)
                .cart(savedCart)
                .quantity(quantity)
                .totalPrice(testProduct.getPrice() * quantity)
                .size(testProduct.getSize())
                .color(testProduct.getColor())
                .build();
        CartProduct save = cartProductRepository.save(cartProduct);
        save.addCart();
        cartProductRepository.deleteById(save.getId());
        save.removeCart();

        assertThat(savedCart.getProducts().isEmpty()).isTrue();
    }
}