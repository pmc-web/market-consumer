package com.pmc.market.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    MANAGER("ROLE_MANAGER","관리자"),
    BUYER("ROLE_BUYER","구매자"),
    SELLER("ROLE_SELLER","판매자");

    private final String key;
    private final String title;
}
