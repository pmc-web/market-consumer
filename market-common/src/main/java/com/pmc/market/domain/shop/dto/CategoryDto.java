package com.pmc.market.domain.shop.dto;

import com.pmc.market.domain.shop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private long categoryId;
    private String mainCategory;
    private String subCategory;

    public static CategoryDto from(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getId())
                .mainCategory(category.getMainCategory())
                .subCategory(category.getSubCategory())
                .build();
    }
}
