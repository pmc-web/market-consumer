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
public class ShopResponseDto {
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
    private long likes;

    public static ShopResponseDto of(Shop shop) {
        return ShopResponseDto.builder()
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
                .likes(shop.getFavorites() == null ? 0 : shop.getFavorites().size())
                .build();
    }

    public static ShopResponseDto of(Shop shop, long likes) {
        return ShopResponseDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .period(shop.getPeriod())
                .fullDescription(shop.getFullDescription())
                .shortDescription(shop.getShortDescription())
                .regDate(shop.getRegDate())
                .businessName(shop.getBusinessName())
                .owner(shop.getOwner())
                .telephone(shop.getTelephone())
                .categoryId(shop.getCategory() == null ? null : shop.getCategory().getId())
                .userId(shop.getUser() == null ? null : shop.getUser().getId())
                .deliveryCost(shop.getDeliveryCost())
                .qnaDescription(shop.getQnaDescription())
                .shipDescription(shop.getShipDescription())
                .likes(likes)
                .build();
    }
}
