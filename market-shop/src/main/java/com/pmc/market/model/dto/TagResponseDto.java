package com.pmc.market.model.dto;

import com.pmc.market.model.entity.ShopTag;
import com.pmc.market.model.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TagResponseDto {
    private Long id;
    private String tagName;
    private List<Long> shopIds = new ArrayList<>();

    public static TagResponseDto from(Tag tag) {
        return TagResponseDto.builder()
                .id(tag.getId())
                .tagName(tag.getName())
                .shopIds(tag.getShopTags().stream().map(ShopTag::getId).collect(Collectors.toList()))
                .build();
    }
}
