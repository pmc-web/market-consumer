package com.pmc.market.service;

import com.pmc.market.model.dto.CartResponseDto;
import com.pmc.market.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartProductRepository cartProductRepository;

    @Override
    public List<CartResponseDto> getUserCart(long userId) {
        return null;
    }
}
