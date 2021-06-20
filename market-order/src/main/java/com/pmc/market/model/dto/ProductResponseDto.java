package com.pmc.market.model.dto;

import com.pmc.market.model.order.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductResponseDto {
    private Long productId;
    private Long orderProductId;
    private String productName;
    private String size;
    private String color;
    private Integer quantity;
    private Integer cost;
    private Integer totalCost;

    public static ProductResponseDto from(OrderProduct orderProduct) {
        return ProductResponseDto.builder()
                .productId(orderProduct.getProduct().getId())
                .orderProductId(orderProduct.getId())
                .productName(orderProduct.getProduct().getName())
                .color(orderProduct.getColor())
                .size(orderProduct.getSize())
                .quantity(orderProduct.getQuantity())
                .cost(orderProduct.getProduct().getPrice())
                .totalCost(orderProduct.getPrice())
                .build();
    }
}
