package com.pmc.market.entity;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE(0, "활성화 상태"),
    PAUSE(1, "일시정지 상태"),
    STOP(2, "정지 상태");

    private Integer id;
    private String title;

    UserStatus(Integer id, String title){
        this.id = id;
        this.title = title;
    }
}
