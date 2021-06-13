package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.user.entity.Cart;
import com.pmc.market.model.user.entity.CartProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartProductRepository cartProductRepository;

    @DisplayName("생성")
    @Test
    void createCartProduct() {
        long userId = 3L;
        Product product = productRepository.findById(4L).get();
        Cart cart = cartRepository.findByUserId(userId).get(0);
        CartProduct cartProduct = CartProduct.builder()
                .product(product)
                .cart(cart)
                .quantity(1)
                .totalPrice(product.getPrice() * 1)
                .size("1")
                .build();
        cartProductRepository.save(cartProduct);
    }

    @DisplayName("장바구니에서 물건 삭제")
    @Test
    void cartProductDelete() {
        long cartProductId = 6L;
        cartProductRepository.deleteById(cartProductId);
    }
}