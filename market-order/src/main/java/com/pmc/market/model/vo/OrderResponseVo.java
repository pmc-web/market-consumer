package com.pmc.market.model.vo;

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
public class OrderResponseVo {
    private Long purchaseId;
    private LocalDateTime regDate;
    private Pay pay;
    private List<ProductResponseVo> products = new ArrayList<>();

    public static OrderResponseVo from(Purchase purchase) {
        return OrderResponseVo.builder()
                .purchaseId(purchase.getId())
                .regDate(purchase.getRegDate())
                .pay(purchase.getPay())
                .products(purchase.getProducts().stream().map(ProductResponseVo::from).collect(Collectors.toList()))
                .build();
    }
}
