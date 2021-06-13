package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.CartProductRequestDto;
import com.pmc.market.model.dto.CartResponseDto;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.Cart;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public Cart createCart(long userId, long shopId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new EntityNotFoundException("해당 마켓이 없습니다."));
        return Cart.builder()
                .regDate(LocalDateTime.now())
                .user(user)
                .shop(shop)
                .build();
    }

    @Transactional
    @Override
    public List<CartResponseDto> getUserCarts(long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream().map(CartResponseDto::from).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CartResponseDto getUserCartByShop(long userId, long shopId) {
        Cart cart = cartRepository.findByUserIdAndShopId(userId, shopId).orElseThrow(() -> new EntityNotFoundException("해당하는 장바구니 정보가 없습니다."));
        return CartResponseDto.from(cart);
    }

    @Override
    public void addToCart(long userId, CartProductRequestDto requestDto) {
        Optional<Cart> isCart = cartRepository.findByUserIdAndShopId(userId, requestDto.getShopId());
        Cart cart;
        if (!isCart.isPresent()) cart = createCart(userId, requestDto.getShopId());
        else cart = isCart.get();
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다,"));
        cartProductRepository.save(requestDto.toEntity(requestDto, cart, product));
    }

    @Override
    public void deleteCart(long cartId) {

    }

    @Override
    public void deleteProductToCart(long cartId, long cartProductId) {

    }

    @Override
    public void updateCartProduct(long cartId, CartProductRequestDto cartProductRequestDto) {

    }
}
