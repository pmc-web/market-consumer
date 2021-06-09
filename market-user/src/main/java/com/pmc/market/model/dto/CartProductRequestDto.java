package com.pmc.market.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@NoArgsConstructor
@ApiModel("상품 장바구니 담기")
public class CartProductRequestDto {

    @ApiModelProperty(value = "상품 id")
    @NotNull
    private Long productId;

    @ApiModelProperty(value = "수량")
    @NotNull
    private Integer quantity;

    @ApiModelProperty(value = "상품 가격")
    @NotNull
    private String price;

    @ApiModelProperty(value = "색상이나 사이즈 기타 정보")
    private String size;

    private String color;

    private String etc;

}
