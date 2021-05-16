package com.pmc.market.model.dto;

import com.pmc.market.model.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    // TODO: 좋아요 정보도 넣어야 하는데 ???? jpa 로 가져올 수 있을지 확인 후
    private long id;
    private String name;
    private LocalDateTime period;
    private String fullDescription;
    private String shortDescription;
    private LocalDateTime regDate;
    private String businessNumber;
    private String businessName;
    private String owner;
    private String telephone;
    private Long categoryId;
    private Long userId;
    private Integer deliveryCost;
    private String qnaDescription;
    private String shipDescription;

    public static ShopDto of(Shop shop) {
        return ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .period(shop.getPeriod())
                .fullDescription(shop.getFullDescription())
                .shortDescription(shop.getShortDescription())
                .regDate(shop.getRegDate())
                .businessName(shop.getBusinessName())
                .businessNumber(shop.getBusinessNumber())
                .owner(shop.getOwner())
                .telephone(shop.getTelephone())
                .categoryId(shop.getCategory() == null ? null : shop.getCategory().getId())
                .userId(shop.getUser() == null ? null : shop.getUser().getId())
                .deliveryCost(shop.getDeliveryCost())
                .qnaDescription(shop.getQnaDescription())
                .shipDescription(shop.getShipDescription())
                .build();
    }
}
