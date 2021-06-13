package com.pmc.market.model.dto;

import com.pmc.market.model.product.entity.Product;
import com.pmc.market.model.user.entity.Cart;
import com.pmc.market.model.user.entity.CartProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("상품 장바구니 담기")
public class CartProductRequestDto {

    @ApiModelProperty(value = "마켓 id")
    @NotNull
    private Long shopId;

    @ApiModelProperty(value = "상품 id")
    @NotNull
    private Long productId;

    @ApiModelProperty(value = "구매 수량")
    @NotNull
    private Integer quantity;

    @ApiModelProperty(value = "상품 전체 가격")
    @NotNull
    private Integer totalPrice;

    @ApiModelProperty(value = "색상이나 사이즈 기타 정보")
    private String size;
    @ApiModelProperty(value = "색상이나 사이즈 기타 정보")
    private String color;


    public CartProduct toEntity(CartProductRequestDto requestDto, Cart cart, Product product) {
        return CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(requestDto.getQuantity())
                .color(requestDto.getColor())
                .totalPrice(requestDto.getTotalPrice())
                .size(requestDto.getSize())
                .build();
    }

    public void updateCart(CartProduct cartProduct) {
        cartProduct.setColor(this.color);
        cartProduct.setQuantity(this.quantity);
        cartProduct.setSize(this.size);
        cartProduct.setTotalPrice(this.totalPrice);
    }
}
