package com.pmc.market.domain.shop.dto;

import com.pmc.market.domain.user.entity.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponseDto {
    private Long productId;
    private String productName;
    private Integer totalPrice;
    private Integer quantity;
    private String size;
    private String color;

    public static CartProductResponseDto from(CartProduct cartProduct) {
        return CartProductResponseDto.builder()
                .productId(cartProduct.getProduct().getId())
                .productName(cartProduct.getProduct().getName())
                .totalPrice(cartProduct.getTotalPrice())
                .quantity(cartProduct.getQuantity())
                .size(cartProduct.getSize())
                .color(cartProduct.getColor())
                .build();
    }
}
