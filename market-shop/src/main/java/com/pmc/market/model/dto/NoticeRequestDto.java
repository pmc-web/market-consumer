package com.pmc.market.model.dto;

import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.shop.entity.ShopNotice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Notice 생성, 수정시 필요한 NoticeInput")
public class NoticeRequestDto {

    @ApiModelProperty(value = "공지사항 제목")
    @NotEmpty
    private String title;

    @ApiModelProperty(value = "공지사항 내용")
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
