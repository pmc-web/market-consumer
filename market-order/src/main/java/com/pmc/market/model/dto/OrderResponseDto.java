package com.pmc.market.model.dto;

import com.pmc.market.model.order.entity.Pay;
import com.pmc.market.model.order.entity.Purchase;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("주문내역")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderResponseDto {
    private Long purchaseId;
    private LocalDateTime regDate;
    private Pay pay;
    private List<ProductResponseDto> products = new ArrayList<>();

    public static OrderResponseDto from(Purchase purchase) {
        return OrderResponseDto.builder()
                .purchaseId(purchase.getId())
                .regDate(purchase.getRegDate())
                .pay(purchase.getPay())
                .products(purchase.getProducts().stream().map(ProductResponseDto::from).collect(Collectors.toList()))
                .build();
    }
}
