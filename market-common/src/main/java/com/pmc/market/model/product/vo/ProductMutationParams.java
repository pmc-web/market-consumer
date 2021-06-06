package com.pmc.market.model.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductMutationParams {

    @NotBlank(message = "상품명을 입력해주세요.")
    @ApiModelProperty(value = "상품명", example = "원피스", required = true, position = 1)
    @NotNull
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    @ApiModelProperty(value = "가격", example = "15000", required = true, position = 2)
    @NotNull
    private Integer price;

    @Nullable
    @ApiModelProperty(value = "수량", example = "3", position = 3)
    private Integer amount;

    @ApiModelProperty(value = "설명", example = "여름에 시원한 하얀 레이어드 원피스", position = 4)
    private String description;
}
