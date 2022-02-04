package com.pmc.market.model.dto;

import com.pmc.market.domain.shop.entity.ShopTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ShopIdNameDto {
    private Long shopId;
    private String shopName;

    public static ShopIdNameDto of(ShopTag shopTag) {
        return ShopIdNameDto.builder()
                .shopId(shopTag.getShop().getId())
                .shopName(shopTag.getShop().getName())
                .build();
    }
}
