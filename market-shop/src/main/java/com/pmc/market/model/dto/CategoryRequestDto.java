package com.pmc.market.model.dto;

import com.pmc.market.domain.shop.entity.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@ApiModel("Category 생성시 ")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryRequestDto {

    @ApiModelProperty(value = "메인 카테고리", example = "악세사리")
    @NotEmpty(message = "mainCategory can not empty.")
    private String mainCategory;

    @ApiModelProperty(value = "서브 카테고리", example = "귀걸이")
    private String subCategory;

    public Category toEntity() {
        return Category.builder()
                .mainCategory(this.mainCategory)
                .subCategory(this.subCategory)
                .build();
    }
}
