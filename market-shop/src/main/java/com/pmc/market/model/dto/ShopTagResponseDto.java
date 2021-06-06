package com.pmc.market.model.dto;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.shop.entity.ShopTag;
import com.pmc.market.model.shop.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ShopTagResponseDto {
    private Long tagId;
    private String tagName;
    private List<ShopIdNameDto> shops;

    public static ShopTagResponseDto from(List<ShopTag> shopTags) {
        if (shopTags == null || shopTags.size() == 0) throw new EntityNotFoundException("해당 태그의 정보가 없습니다.");
        Tag tag = shopTags.get(0).getTag();
        List<ShopIdNameDto> shops = shopTags.stream().map(ShopIdNameDto::of).collect(Collectors.toList());
        return ShopTagResponseDto.builder()
                .tagId(tag.getId())
                .tagName(tag.getName())
                .shops(shops)
                .build();
    }

    public static ShopTagResponseDto of(Tag tag, List<ShopIdNameDto> shopTags) {
        return ShopTagResponseDto.builder()
                .tagId(tag.getId())
                .tagName(tag.getName())
                .shops(shopTags)
                .build();
    }
}
