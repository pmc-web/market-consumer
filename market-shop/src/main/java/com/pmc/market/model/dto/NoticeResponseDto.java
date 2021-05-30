package com.pmc.market.model.dto;

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
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private Long shopId;

    public static NoticeResponseDto of(ShopNotice shopNotice) {
        return NoticeResponseDto.builder()
                .id(shopNotice.getId())
                .title(shopNotice.getTitle())
                .content(shopNotice.getContent())
                .regDate(shopNotice.getRegDate())
                .shopId(shopNotice.getShop().getId()) // TODO 이렇게 해도 되는지 확인하기
                .build();
    }
}
