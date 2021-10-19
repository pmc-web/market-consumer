package com.pmc.market.model.dto;

import com.pmc.market.model.user.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartResponseDto {
    private Long cartId;
    private Long shopId;
    private LocalDateTime regDate;
    private List<CartProductResponseDto> products = new ArrayList<>();

    public static CartResponseDto from(Cart cart) {
        return CartResponseDto.builder()
                .cartId(cart.getId())
                .shopId(cart.getShop().getId())
                .regDate(cart.getRegDate())
                .products(cart.getProducts().stream().map(CartProductResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}
