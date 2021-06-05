package com.pmc.market.model.dto;

import com.pmc.market.model.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagIdNameDto {

    private long id;
    private String tagName;

    public static TagIdNameDto from(Tag tag) {
        return TagIdNameDto.builder()
                .id(tag.getId())
                .tagName(tag.getName())
                .build();
    }
}
