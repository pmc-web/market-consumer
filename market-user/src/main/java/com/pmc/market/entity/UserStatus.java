package com.pmc.market.entity;

import lombok.Getter;

@Getter
public enum  UserStatus {

    MANAGER("관리자", 0),
    BUYER("구매자", 1),
    SELLER("판매자", 2);

    private String userStatus;
    private int userLevel;

    UserStatus(String userStatus, int userLevel){
        this.userStatus = userStatus;
        this.userLevel = userLevel;
    }

}
