package com.pmc.market.model.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QnAType {
    DELIVERY("배송문의"),
    PRODUCT("상품문의"),
    OTHER("기타문의");

    private String typeName;
}
