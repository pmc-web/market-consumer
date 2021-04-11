package com.pmc.market.entity;

import lombok.Getter;

@Getter
public enum UserRole {

    MANAGER(0,"관리자"),
    BUYER(1,"구매자"),
    SELLER(2,"판매자");

    private Integer id;
    private String title;

    UserRole(Integer id, String title){
       this.id = id;
       this.title = title;
    }

}
