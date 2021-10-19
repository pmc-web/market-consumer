package com.pmc.market.service;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.model.dto.CartProductRequestDto;
import com.pmc.market.model.dto.CartResponseDto;
import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.Cart;
import com.pmc.market.model.user.entity.CartProduct;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
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

    @Override
    public List<CartResponseDto> getUserCarts(long userId) {
        List<Cart> carts = cartRepository.findByUser_IdOrderByRegDateDesc(userId);
        return carts.stream().map(CartResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public CartResponseDto getUserCartByShop(long userId, long shopId) {
        Cart cart = cartRepository.findByUser_IdAndShop_Id(userId, shopId).orElseThrow(() -> new EntityNotFoundException("해당하는 장바구니 정보가 없습니다."));
        return CartResponseDto.from(cart);
    }

    @Override
    public void addToCart(long userId, CartProductRequestDto requestDto) {
        Optional<Cart> isCart = cartRepository.findByUser_IdAndShop_Id(userId, requestDto.getShopId());
        Cart cart;
        if (!isCart.isPresent()) {
            cart = createCart(userId, requestDto.getShopId());
            cartRepository.save(cart);
        } else cart = isCart.get();
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다,"));
        cartProductRepository.save(requestDto.toEntity(requestDto, cart, product));
    }

    @Override
    public void deleteCart(long cartId) {
        cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("해당 장바구니가 없습니다."));
        cartRepository.deleteById(cartId);
    }

    @Override
    public void deleteProductToCart(long cartId, long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다"));
        if (!cartProduct.getCart().getId().equals(cartId))
            throw new BusinessException("cartId - 입력값이 올바르지 않습니다", ErrorCode.INVALID_INPUT_VALUE);
        cartProductRepository.deleteById(cartProductId);
    }

    @Override
    public void updateCartProduct(long cartId, CartProductRequestDto cartProductRequestDto) {
        CartProduct cartProduct = cartProductRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("해당 상품이 없습니다."));
        cartProductRequestDto.updateCart(cartProduct);
    }
}
