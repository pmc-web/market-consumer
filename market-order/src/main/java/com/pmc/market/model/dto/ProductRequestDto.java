package com.pmc.market.model.dto;

import com.pmc.market.model.order.entity.OrderProduct;
import com.pmc.market.model.order.entity.Order;
import com.pmc.market.model.product.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("주문할 때 필요한 상품 정보")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductRequestDto {
    @ApiModelProperty("상품 Id")
    private Long productId;

    @ApiModelProperty("해당 상품 전체 가격")
    private Integer totalPrice;

    @ApiModelProperty("해당상품 갯수")
    private Integer totalQuantity;

    @ApiModelProperty("상품 사이즈 정보")
    private String size;

    @ApiModelProperty("상품 컬러 정보")
    private String color;

    public OrderProduct toEntity(ProductRequestDto requestDto, Product product, Order order) {
        return OrderProduct.builder()
                .product(product)
                .quantity(requestDto.getTotalQuantity())
                .price(requestDto.getTotalPrice())
                .size(requestDto.getSize())
                .color(requestDto.getColor())
                .order(order)
                .build();
    }

}
