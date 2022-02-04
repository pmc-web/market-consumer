package com.pmc.market.model.dto;

import com.pmc.market.domain.shop.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@ApiModel("Tag 생성시")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TagRequestDto {
    @ApiModelProperty(value = "태그 이름", example = "핸드메이드")
    @NotEmpty(message = "tag name can not empty.")
    private String name;

    public Tag toEntity() {
        return Tag.builder()
                .name(this.name)
                .build();
    }
}
