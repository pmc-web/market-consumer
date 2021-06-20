package com.pmc.market.model.dto;

import com.pmc.market.model.order.entity.Pay;
import com.pmc.market.model.order.entity.Purchase;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel("주문할 때 필요한 정보")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderRequestDto {
//    private Long userId;

    @NotNull
    @ApiModelProperty("구매한 마켓 id")
    private Long shopId;

    @NotNull
    @ApiModelProperty("상품 list")
    private List<ProductRequestDto> products = new ArrayList<>();

    @ApiModelProperty("상품 총 갯수")
    private Integer amount;

    @ApiModelProperty("결제 방법")
    private Pay pay;

    @ApiModelProperty("전체 상품 가격")
    private Integer totalProductPrice;

    @ApiModelProperty("전체 가격")
    private Integer totalPrice;

    @ApiModelProperty("배송지 정보 - 전체 주소")
    private String shipAddress;

    @ApiModelProperty("배송지 정보 - 우편 번호")
    private String zipCode;

    @ApiModelProperty("수취인 이름")
    private String name;

    @ApiModelProperty("수취인 전화번호")
    private String phoneNumber;

    @ApiModelProperty("배송비")
    private Integer shipCost;

    @ApiModelProperty("유저 Id")
    private Long userId;

    public Purchase toEntity(OrderRequestDto requestDto, Shop shop, User user) {
        return Purchase.builder()
                .pay(pay)
                .shop(shop)
                .user(user)
                .amount(requestDto.getAmount())
                .shipCost(requestDto.getShipCost())
                .totalPrice(requestDto.getTotalPrice())
                .shipAddress(requestDto.getShipAddress())
                .zipCode(requestDto.getZipCode())
                .regDate(LocalDateTime.now())
                .build();
    }

}
