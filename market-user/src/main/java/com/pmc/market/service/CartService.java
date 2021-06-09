package com.pmc.market.service;

import com.pmc.market.model.dto.CartResponseDto;

import java.util.List;

public interface CartService {
    List<CartResponseDto> getUserCart(long userId);
}
