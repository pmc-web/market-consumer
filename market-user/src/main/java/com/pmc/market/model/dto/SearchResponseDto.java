package com.pmc.market.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchResponseDto {
    private long count;
    private String keyword;

    public static SearchResponseDto of(long count, String keyword) {
        return SearchResponseDto.builder()
                .count(count)
                .keyword(keyword)
                .build();
    }
}
