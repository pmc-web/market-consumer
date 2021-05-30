package com.pmc.market.model.dto;

import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.entity.ShopNotice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRequestDto {
    private String title;
    private String content;

    public ShopNotice toEntity(NoticeRequestDto noticeRequestDto, Shop shop) {
        return ShopNotice
                .builder()
                .regDate(LocalDateTime.now())
                .title(noticeRequestDto.getTitle())
                .content(noticeRequestDto.getContent())
                .shop(shop)
                .build();
    }
}
