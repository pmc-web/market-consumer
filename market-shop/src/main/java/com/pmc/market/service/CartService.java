package com.pmc.market.service;

import com.pmc.market.model.dto.CartProductRequestDto;
import com.pmc.market.model.dto.CartResponseDto;

import java.util.List;

public interface CartService {
    List<CartResponseDto> getUserCarts(long userId);

    CartResponseDto getUserCartByShop(long userId, long shopId);

    void addToCart(long userId, CartProductRequestDto cartProductRequestDto);

    void deleteCart(long cartId);

    void deleteProductToCart(long cartId, long cartProductId);

    void updateCartProduct(long cartId, CartProductRequestDto cartProductRequestDto);

}
