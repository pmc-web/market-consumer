package com.pmc.market.model.shop.entity;

import lombok.Getter;

@Getter
public enum ShopImageType {
    MAIN_IMAGE("MAIN", "메인 이미지"),
    SUB_IMAGE("SUB", "서브 이미지");

    private String title;
    private String description;

    ShopImageType(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
